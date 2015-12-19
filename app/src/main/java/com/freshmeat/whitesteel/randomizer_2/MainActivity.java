package com.freshmeat.whitesteel.randomizer_2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.freshmeat.whitesteel.randomizer_2.formats.HD_Choice;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener {


    final String LOG_TAG = "mLOGs";
    private FragmentManager manager;
    private SharedPreferences.Editor editor;
    public static final String APP_PREFERENCE = "mySettings";
    public static final String HD_Switchers = "HD_Switcher";
    public static final String HD_Resolution = "HD_Walls";

    private SharedPreferences sharedPreferences;
    final String RESOLUTION_SETTINGS = "settingsR";
    final String GENRES_SETTINGS = "settingsG";

    int number;

    public Intent startIntent;
    Intent intentResolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();


        startIntent = new Intent(Intent.ACTION_MAIN);
//        startIntent.addCategory(Intent.CATEGORY_HOME);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadSaves();
    }

    public void btnClick(View view) {
        Intent intent = new Intent(this, HD_Choice.class);
        startActivity(intent);
    }

    private void saveSaves(){
        intentResolution = getIntent();
        if(intentResolution.hasExtra("720")){
            editor.putString(RESOLUTION_SETTINGS, "Ch720");
            Log.d(LOG_TAG, "Ch720 сохранен");
        }else if(intentResolution.hasExtra("900")){
            editor.putString(RESOLUTION_SETTINGS, "Ch900");
            Log.d(LOG_TAG, "Ch900 сохранен");
        }else if(intentResolution.hasExtra("1080")){
            editor.putString(RESOLUTION_SETTINGS, "Ch1080");
            Log.d(LOG_TAG, "Ch1080 сохранен");
        }else if(intentResolution.hasExtra("1440")){
            editor.putString(RESOLUTION_SETTINGS, "Ch1440");
            Log.d(LOG_TAG, "Ch1440 сохранен");
        }
        else {
            Log.d(LOG_TAG, "Nothing to save");
        }
    }

    private void loadSaves(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedSettings = sharedPreferences.getString(RESOLUTION_SETTINGS, "");
        Intent intentDialog = new Intent(this, NoticeDialogFragment.class);
        intentDialog.putExtra(savedSettings, number);
        Log.d(LOG_TAG, savedSettings);
    }

    protected void saveGenres(String text){
        if(text.equals("Ch720")){
            Log.d(LOG_TAG, text + " saveGenres");
        }else if(text.equals("Ch900")){
            Log.d(LOG_TAG, text + " saveGenres");
        }
    }

    protected void saveGenre(ArrayList arrayList){

            switch (){
                case 0:
                    Log.d(LOG_TAG, "РАБОТАЕТ!!!!!!");
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

        @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.GENRESHD:
                DialogFragment dialog1 = new NoticeDialogFragment();
                if(getIntent().hasExtra("720")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment1");

                }
                else if(getIntent().hasExtra("900")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment2");

                }
                else if(getIntent().hasExtra("1080")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment3");

                }
                else if(getIntent().hasExtra("1440")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment4");

                }
                else {
                    Toast.makeText(getApplicationContext(), "Haven't choosen Resolution", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        editor.commit();
    }


    public void btnSavePress(View view){
        saveSaves();
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