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



public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener {


    final String LOG_TAG = "mLOGs";

    //https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
    TinyDB tinyDB;
    public Intent startIntent;
    Intent intentResolution;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        loadResolution();
    }

    //Intent to HD_Choice
    public void btnClick(View view) {
        Intent intent = new Intent(this, HD_Choice.class);
        startActivity(intent);
    }


    //Размер экрана
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

    //Жанры
    //Преобразует ArrayList в int[]
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
                    Toast.makeText(getApplicationContext(), "Haven't choosen Resolution", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        saveResolution();
        makeGenreArray();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!getIntent().hasExtra("720") ||
                !getIntent().hasExtra("900") ||
                !getIntent().hasExtra("1080") ||
                !getIntent().hasExtra("1440")) {
            finish();
            Log.d(LOG_TAG, "Pabotaet BblXoD!!");
        }
    }
}