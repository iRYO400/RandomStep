package com.freshmeat.whitesteel.randomizer_2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.freshmeat.whitesteel.randomizer_2.formats.HD_Resol_btn;
import com.freshmeat.whitesteel.randomizer_2.formats.Wide_Resol_btn;
import com.freshmeat.whitesteel.randomizer_2.genres.GenresHD;
import com.freshmeat.whitesteel.randomizer_2.genres.GenresWide;



public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener, NoticeDialogFragmentWide.NoticeDialogListener  {


    private HD_Resol_btn hd_resol_btn;
    private Wide_Resol_btn wide_resol_btn;
    private GenresHD genresHD;
    private GenresWide genresWide;

    final String LOG_TAG = "mLOGs";

    private FragmentManager manager;
    private FragmentTransaction transaction;

    public Switch aSwitch;
    private SharedPreferences.Editor editor;
    public static final String APP_PREFERENCE = "mySettings";
    public static final String HD_Switchers = "HD_Switcher";
    public static final String Wide_Switchers = "Wide_Switcher";
    public static final String HD_Resolution = "HD_Walls";
    public static final String Wide_Resolution = "WIDE_Walls";
    private SharedPreferences mSettings;
    public Intent startIntent;
    String str1;
    String str2;
    int a;
    Bundle bundle = new Bundle();

    boolean addList = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch = (Switch) findViewById(R.id.switch1);
        hd_resol_btn = new HD_Resol_btn();
        wide_resol_btn = new Wide_Resol_btn();
        manager = getSupportFragmentManager();
        genresHD = new GenresHD();
        genresWide = new GenresWide();

        startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_HOME);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        transaction = manager.beginTransaction();

//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(getIntent().hasExtra("800")){
//                    aSwitch.setChecked(true);
//                    transaction.add(R.id.hdWideContainer, wide_resol_btn, Wide_Resol_btn.TAG);
//                    transaction.add(R.id.genres_container, genresWide, GenresWide.TAG);
//                    Log.d(LOG_TAG,"Ne Huinya");
//                }
//                else {
//                    Log.d(LOG_TAG, "Huinya");
//
//                }
//                transaction.commit();
//
//            }
//        });
        mSettings = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);

    }

    public void onClickFragmentHDorWide1(View view) {

        transaction = manager.beginTransaction();

        switch (view.getId()) {

            case R.id.switch1:
                if (manager.findFragmentByTag(hd_resol_btn.TAG) != null) {
                    transaction.replace(R.id.hdWideContainer, wide_resol_btn, Wide_Resol_btn.TAG);
                    editor = mSettings.edit();
                    editor.putString(Wide_Switchers, "Wide");
            //        aSwitch.setChecked(true);
                    Log.d(LOG_TAG, "editor, putString Wide");
                    editor.apply();
                }
                if (manager.findFragmentByTag(wide_resol_btn.TAG) != null){
                    transaction.replace(R.id.hdWideContainer, hd_resol_btn, HD_Resol_btn.TAG);
                    editor = mSettings.edit();
                    editor.putString(HD_Switchers, "HD");
          //          aSwitch.setChecked(false);
                    Log.d(LOG_TAG, "editor, putString HD");
                    editor.apply();
                }
                if (manager.findFragmentByTag(genresHD.TAG) != null){
                    transaction.replace(R.id.genres_container, genresWide, GenresWide.TAG);
                }
                if(manager.findFragmentByTag(genresWide.TAG) != null){
                    transaction.replace(R.id.genres_container, genresHD, GenresHD.TAG);
                }
                break;
            }

        transaction.commit();

    }

    @Override
    protected void onResume() {

        transaction = manager.beginTransaction();


        if(!bundle.getBoolean("True",false)){
            str1 = mSettings.getString(HD_Switchers, "HD");
            transaction.add(R.id.hdWideContainer, hd_resol_btn, HD_Resol_btn.TAG);
            transaction.add(R.id.genres_container, genresHD, GenresHD.TAG);
            Log.d(LOG_TAG,str1 +" sdfsd");
        }
        else if(bundle.getBoolean("False", true)){
            str2 = mSettings.getString(Wide_Switchers,"Wide");
            transaction.add(R.id.hdWideContainer, wide_resol_btn, Wide_Resol_btn.TAG);
            transaction.add(R.id.genres_container, genresWide, GenresWide.TAG);
            aSwitch.isChecked();
            Log.d(LOG_TAG, str2+"  sdfsd");
        }
        transaction.commit();
        super.onResume();
    }

    @Override
    protected void onPause() {
        this.addList = manager.getFragments().isEmpty();
            if(aSwitch.isChecked()){
                bundle.putBoolean("False", true);

                Log.d(LOG_TAG, "Skotina");

            }else if(!aSwitch.isChecked()) {
                bundle.putBoolean("True", false);
                Log.d(LOG_TAG,"Suka");

            }
            transaction = manager.beginTransaction();
            transaction.remove(this.hd_resol_btn);
            transaction.remove(this.genresHD);
            transaction.remove(this.wide_resol_btn);
            transaction.remove(this.genresWide);
            transaction.commit();

        super.onPause();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

        @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.genres_go_hd_btn:
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
                break;
            case R.id.genres_go_wide_btn:
                DialogFragment dialog2 = new NoticeDialogFragmentWide();
                if(getIntent().hasExtra("800")) {
                    dialog2.show(getSupportFragmentManager(), "NoticeDialogFragmentWide");
                }
                if(getIntent().hasExtra("1050")){
                    dialog2.show(getSupportFragmentManager(), "NoticeDialogFragment2");

                }
                if(getIntent().hasExtra("1200")){
                    dialog2.show(getSupportFragmentManager(), "NoticeDialogFragment3");

                }
                if(getIntent().hasExtra("1600")){
                    dialog2.show(getSupportFragmentManager(), "NoticeDialogFragment4");

                }

                break;
        }
    }


    @Override
    public void onBackPressed() {

        if(!getIntent().hasExtra("720")||
                !getIntent().hasExtra("800")||
                getIntent().hasExtra("900")||
                getIntent().hasExtra("1080")||
                getIntent().hasExtra("1050")||
                getIntent().hasExtra("1440")||
                getIntent().hasExtra("1200")||
                getIntent().hasExtra("1600")) {
            startActivity(startIntent);
            Log.d(LOG_TAG,"Pabotaet BblXoD!!");
        }
    }
}