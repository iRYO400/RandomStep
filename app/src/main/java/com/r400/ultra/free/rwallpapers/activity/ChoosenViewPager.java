package com.r400.ultra.free.rwallpapers.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.nvanbenschoten.motion.ParallaxImageView;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.fragments.images.Constants;
import com.r400.ultra.free.rwallpapers.fragments.images.ImagePagerFragment;

import java.io.File;
import java.util.ArrayList;

public class ChoosenViewPager extends FragmentActivity {

    Fragment fr;
    String tag;
    private TinyDB tinyDB;
    private ArrayList<String> selectedGenresReady;
    private ArrayList<String> selectedGenresArray;
    ParallaxImageView parallaxImageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer);

        parallaxImageView = (ParallaxImageView)findViewById(R.id.parallaxImg);

        Typeface CF = Typeface.createFromAsset(getAssets(), getString(R.string.inside_font));
        textView = (TextView) findViewById(R.id.helpText);
        ((TextView) findViewById(R.id.helpText)).setTypeface(CF);
        textView.setText(R.string.helpTip2);
        tag = ImagePagerFragment.class.getSimpleName();
        fr = getSupportFragmentManager().findFragmentByTag(tag);
        if (fr == null) {
            fr = new ImagePagerFragment();
            fr.setArguments(getIntent().getExtras());
        }
        selectedGenresArray = new ArrayList<>();
        selectedGenresReady = new ArrayList<>();
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
        getSelectedGenres();
    }

    private void getSelectedGenres(){
        tinyDB = new TinyDB(this);
        selectedGenresArray = tinyDB.getListString("toDeployArrayList");
        Toast.makeText(this, "Selected :" + selectedGenresArray.size(), Toast.LENGTH_SHORT).show();
        loadSelectedGenres();
    }

    @Override
    protected void onResume() {
        super.onResume();
        parallaxImageView.registerSensorManager();
        parallaxImageView.setImageResource(R.drawable.dark_back4);
    }


    @Override
    protected void onPause() {
        super.onPause();
        parallaxImageView.unregisterSensorManager();
    }

    private void loadSelectedGenres(){
        for(int i = 0; i < selectedGenresArray.size(); i++) {
            switch (selectedGenresArray.get(i)) {
                case "3D and Abstract":
                    selectedGenresReady.add(Constants.ABSTRACT);
                    break;
                case "Fantasy":
                    selectedGenresReady.add(Constants.FANTASY);
                    break;
                case "Gaming":
                    selectedGenresReady.add(Constants.GAMING);
                    break;
                case "Vector":
                    selectedGenresReady.add(Constants.VECTOR);
                    break;
                case "Cartoons":
                    selectedGenresReady.add(Constants.CARTOONS);
                    break;
                case "Celebrities":
                    selectedGenresReady.add(Constants.CELEBRITIES);
                    break;
                case "Films":
                    selectedGenresReady.add(Constants.FILMS);
                    break;
                case "Animals":
                    selectedGenresReady.add(Constants.ANIMALS);
                    break;
                case "Cities":
                    selectedGenresReady.add(Constants.CITIES);
                    break;
                case "Nature":
                    selectedGenresReady.add(Constants.NATURE);
                    break;
                case "Seasons":
                    selectedGenresReady.add(Constants.SEASONS);
                    break;
                case "Space":
                    selectedGenresReady.add(Constants.SPACE);
                    break;
                case "Aircraft":
                    selectedGenresReady.add(Constants.AIRCRAFT);
                    break;
                case "Bikes":
                    selectedGenresReady.add(Constants.BIKES);
                    break;
                case "Cars":
                    selectedGenresReady.add(Constants.CARS);
                    break;
            }
        }
        tinyDB.putListString("ReadyGenres", selectedGenresReady);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }
}

