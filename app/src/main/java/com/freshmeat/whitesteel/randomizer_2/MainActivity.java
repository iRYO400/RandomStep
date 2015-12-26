package com.freshmeat.whitesteel.randomizer_2;


import android.content.Intent;
import android.content.SharedPreferences;
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
    //SharedPreference настройки
    public static final String RESOLUTION_SETTINGS = "settingsR";
    public static final String GENRES_SETTINGS = "settingsG";
    public static final String APP_PREFERENCE_RESOLUTION = "Resolution";
    public static final String APP_PREFERENCE_GENRE = "Genres";
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editorGenre;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesGenres;




    public Intent startIntent;
    Intent intentResolution;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        sharedPreferences = getSharedPreferences(RESOLUTION_SETTINGS , MODE_PRIVATE);
        sharedPreferencesGenres = getSharedPreferences(GENRES_SETTINGS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editorGenre = sharedPreferencesGenres.edit();
    }

    //Intent to HD_Choice
    public void btnClick(View view) {
        Intent intent = new Intent(this, HD_Choice.class);
        startActivity(intent);
    }


    //Размер экрана
    private void saveResolution() {
        intentResolution = getIntent();
        if (intentResolution.hasExtra("720")) {
            editor.putString(APP_PREFERENCE_RESOLUTION, "Genre720");
            Log.d(LOG_TAG, "Ch720 сохранен");
        } else if (intentResolution.hasExtra("900")) {
            editor.putString(APP_PREFERENCE_RESOLUTION, "Genre900");
            Log.d(LOG_TAG, "Ch900 сохранен");
        } else if (intentResolution.hasExtra("1080")) {
            editor.putString(APP_PREFERENCE_RESOLUTION, "Genre1080");
            Log.d(LOG_TAG, "Ch1080 сохранен");
        } else if (intentResolution.hasExtra("1440")) {
            editor.putString(APP_PREFERENCE_RESOLUTION, "Genre1440");
            Log.d(LOG_TAG, "Ch1440 сохранен");
        } else {
            Log.d(LOG_TAG, "Nothing to save");
        }
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
        for( int j = 0; j < arrayList.size(); j ++) {
            switch (arrayList.get(j)) {
                case 0:
                    Log.d(LOG_TAG, "CG");
                    editorGenre.putString(APP_PREFERENCE_GENRE, "CG");
                    break;
                case 1:
                    Log.d(LOG_TAG, "Games");
                    editorGenre.putString(APP_PREFERENCE_GENRE, "Games");
                    break;
                case 2:
                    Log.d(LOG_TAG, "World");
                    editorGenre.putString(APP_PREFERENCE_GENRE, "World");
                    break;
                case 3:
                    Log.d(LOG_TAG, "Sport");
                    editorGenre.putString(APP_PREFERENCE_GENRE, "Sport");
                    break;
                case 4:
                    Log.d(LOG_TAG, "Auto");
                    editorGenre.putString(APP_PREFERENCE_GENRE, "Auto");
                    break;
            }
        }
    }


    private void loadResolution() {
        String savedSettings = sharedPreferences.getString(RESOLUTION_SETTINGS, "");
        String savedGenres = sharedPreferencesGenres.getString(GENRES_SETTINGS, "");
        Intent intentLoad = getIntent();
        intentLoad.putExtra(savedSettings, 1);
        Log.d(LOG_TAG, savedSettings);
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
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }


    public void btnSavePress(View view) {
        makeGenreArray();
        saveResolution();
        editor.apply();
        editorGenre.apply();
    }

    public void btnLoadPress(View view) {
        loadResolution();
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