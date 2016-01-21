package com.freshmeat.whitesteel.randomizer_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.freshmeat.whitesteel.randomizer_2.formats.HD_Choice;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener {


    final String LOG_TAG = "mLOGs";

    //https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
    private TinyDB tinyDB;
    private Intent intentResolution;
    private ArrayList<String> arrayListGenres;
    ArrayList<String> arrayListFull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadResolution();
    }

    //Intent to HD_Choice
    public void btnClick(View view) {
        Intent intent = new Intent(this, HD_Choice.class);
        startActivity(intent);
    }


    //Сохранение размера экрана
    private void saveResolution() {
        tinyDB = new TinyDB(this);
        intentResolution = getIntent();
        if (intentResolution.hasExtra("720")) {
            tinyDB.putString("MyResolution", "Resolution720");
            Log.d(LOG_TAG, "Ch720 сохранен");
        } else if (intentResolution.hasExtra("900")) {
            tinyDB.putString("MyResolution", "Resolution900");
            Log.d(LOG_TAG, "Ch900 сохранен");
        } else if (intentResolution.hasExtra("1080")) {
            tinyDB.putString("MyResolution", "Resolution1080");
            Log.d(LOG_TAG, "Ch1080 сохранен");
        } else if (intentResolution.hasExtra("1440")) {
            tinyDB.putString("MyResolution", "Resolution1440");
            Log.d(LOG_TAG, "Ch1440 сохранен");
        } else {
            Log.d(LOG_TAG, "Nothing to save");
        }
        Log.d(LOG_TAG, "Дошел до editor.apply");
    }


    //Обработка разрешения
    protected void makeGenreArray() {
        Intent intentGenres = getIntent();
        ArrayList<Integer> arrayList = null;
        if(intentGenres.hasExtra("720Bundle")){
            arrayList = intentGenres.getIntegerArrayListExtra("720Bundle");
            Log.d(LOG_TAG, "Extra 720Bundle " + arrayList);
        } else if (intentGenres.hasExtra("900Bundle")){
            arrayList = intentGenres.getIntegerArrayListExtra("900Bundle");
        }else if (intentGenres.hasExtra("1080Bundle")){
            arrayList = intentGenres.getIntegerArrayListExtra("1080Bundle");
        }else if (intentGenres.hasExtra("1440Bundle")){
            arrayList = intentGenres.getIntegerArrayListExtra("1440Bundle");
        }else {
            Log.d(LOG_TAG, "Nothing to save");
        }
        makeAnArray(arrayList);
    }


    //Выбор жанров
    private void makeAnArray(ArrayList<Integer> arrayList){
        ArrayList<String> arrayListGenres = new ArrayList<String>();
        tinyDB = new TinyDB(this);
        for( int j = 0; j < arrayList.size(); j ++) {
            switch (arrayList.get(j)) {
                case 0:
                    Log.d(LOG_TAG, "CG");
                    arrayListGenres.add("CG");
                    break;
                case 1:
                    Log.d(LOG_TAG, "Games");
                    arrayListGenres.add("Games");
                    break;
                case 2:
                    Log.d(LOG_TAG, "World");
                    arrayListGenres.add("World");
                    break;
                case 3:
                    Log.d(LOG_TAG, "Sport");
                    arrayListGenres.add("Sport");
                    break;
                case 4:
                    Log.d(LOG_TAG, "Auto");
                    arrayListGenres.add("Auto");
                    break;
            }
            tinyDB.putListString("MyGenres", arrayListGenres);
        }
    }

    //Загрузка разрешения
    private void loadResolution() {
        tinyDB = new TinyDB(this);
        String loadedResolution = tinyDB.getString("MyResolution");
        ArrayList<String> loadedGenres = tinyDB.getListString("MyGenres");
        Log.d(LOG_TAG, " LOADING " + " " + loadedResolution + " " + loadedGenres);
    }

    //Button for opening "Choose Genres"
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.GENRESHD:
                DialogFragment dialog1 = new NoticeDialogFragment();
                if (getIntent().hasExtra("720") || getIntent().hasExtra("Genre720")) {
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment1");
                } else if (getIntent().hasExtra("900") || getIntent().hasExtra("Genre900")) {
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment2");
                } else if (getIntent().hasExtra("1080") || getIntent().hasExtra("Genre1080")) {
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment3");
                } else if (getIntent().hasExtra("1440") || getIntent().hasExtra("Genre1440")) {
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment4");
                } else {
                    Toast.makeText(getApplicationContext(), "Please, choose genre(-s).", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        saveResolution();
        makeGenreArray();
        loadBitmaps();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"Click BACK again",Toast.LENGTH_SHORT).show();
        if (!getIntent().hasExtra("720") ||
                !getIntent().hasExtra("900") ||
                !getIntent().hasExtra("1080") ||
                !getIntent().hasExtra("1440")) {
            finish();
            Log.d(LOG_TAG, "Pabotaet BblXoD!!");
        }
    }

    public void loadBitmaps(){
        switch (tinyDB.getString("MyResolution")){
            case "Resolution720":
                arrayFullHDHD();
                break;
            case "Resolution900":
                arrayFullHDHD();
                break;
            case "Resolution1080":
                arrayFullHD2K();
                break;
            case "Resolution1440":
                arrayFullHD2K();
                break;
        }
    }

    //2K resolution
    public void arrayFullHD2K(){
        arrayListFull = new ArrayList<>();
        tinyDB = new TinyDB(this);
        arrayListGenres = tinyDB.getListString("MyGenres");
        for(int i = 0; i < arrayListGenres.size(); i++){
            switch (arrayListGenres.get(i)) {
                case "CG":
                    ArrayList<String> arrayListCG = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.CG2K)));
                    arrayListFull.addAll(arrayListCG);
                    break;
                case "Games":
                    ArrayList<String> arrayListGames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Games2K)));
                    arrayListFull.addAll(arrayListGames);
                    break;
                case "World":
                    ArrayList<String> arrayListWorld = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.World2K)));
                    arrayListFull.addAll(arrayListWorld);
                    break;
                case "Sport":
                    ArrayList<String> arrayListSport = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Movies2K)));
                    arrayListFull.addAll(arrayListSport);
                    break;
                case "Auto":
                    ArrayList<String> arrayListAuto = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Crafts2K)));
                    arrayListFull.addAll(arrayListAuto);
                    break;
            }
        }
        tinyDB.putListString("2K_OptimizedArray", arrayListFull);
        Log.d(LOG_TAG, " loadBitmap array2K " + arrayListGenres + " " + arrayListFull);
    }

    //HD Resolution
    public void arrayFullHDHD(){
        arrayListFull = new ArrayList<>();
        tinyDB = new TinyDB(this);
        arrayListGenres = tinyDB.getListString("MyGenres");
        for(int i = 0; i < arrayListGenres.size(); i++){
            switch (arrayListGenres.get(i)) {
                case "CG":
                    ArrayList<String> arrayListCG = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.CGHD)));
                    arrayListFull.addAll(arrayListCG);
                    break;
                case "Games":
                    ArrayList<String> arrayListGames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.GamesHD)));
                    arrayListFull.addAll(arrayListGames);
                    break;
                case "World":
                    ArrayList<String> arrayListWorld = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.WorldHD)));
                    arrayListFull.addAll(arrayListWorld);
                    break;
                case "Sport":
                    ArrayList<String> arrayListSport = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.MoviesHD)));
                    arrayListFull.addAll(arrayListSport);
                    break;
                case "Auto":
                    ArrayList<String> arrayListAuto = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.CraftsHD)));
                    arrayListFull.addAll(arrayListAuto);
                    break;
            }
        }
        tinyDB.putListString("HD_OptimizedArray", arrayListFull);
        Log.d(LOG_TAG, " loadBitmap arrayHD " + arrayListGenres + " " + arrayListFull);
    }
}