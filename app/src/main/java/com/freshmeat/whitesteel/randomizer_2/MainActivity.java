package com.freshmeat.whitesteel.randomizer_2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.freshmeat.whitesteel.randomizer_2.formats.HD_Choice;
import com.freshmeat.whitesteel.randomizer_2.formats.HD_Resol_btn;
import com.freshmeat.whitesteel.randomizer_2.genres.GenresHD;



public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener, NoticeDialogFragmentWide.NoticeDialogListener  {


    private HD_Resol_btn hd_resol_btn;
    private GenresHD genresHD;
    final String LOG_TAG = "mLOGs";

    private FragmentManager manager;


    private SharedPreferences.Editor editor;
    public static final String APP_PREFERENCE = "mySettings";
    public static final String HD_Switchers = "HD_Switcher";
    public static final String HD_Resolution = "HD_Walls";
    private SharedPreferences mSettings;
    public Intent startIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hd_resol_btn = new HD_Resol_btn();

        manager = getSupportFragmentManager();
        genresHD = new GenresHD();

        startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_HOME);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mSettings = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);

    }

    public void btnClick(View view) {
        Intent intent = new Intent(this, HD_Choice.class);
        startActivity(intent);
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
                if(getIntent().hasExtra("900")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment2");

                }
                if(getIntent().hasExtra("1080")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment3");

                }
                if(getIntent().hasExtra("1440")){
                    dialog1.show(getSupportFragmentManager(), "NoticeDialogFragment4");

                }
                else {
                    Toast.makeText(getApplicationContext(), "Haven't choosen Resolution", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {

        if(!getIntent().hasExtra("720")||
                !getIntent().hasExtra("900")||
                !getIntent().hasExtra("1080")||
                !getIntent().hasExtra("1440")) {
            startActivity(startIntent);
            finish();
            Log.d(LOG_TAG,"Pabotaet BblXoD!!");
        }
        else {
            super.onBackPressed();
        }
    }

}