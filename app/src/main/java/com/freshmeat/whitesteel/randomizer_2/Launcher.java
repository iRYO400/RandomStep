package com.freshmeat.whitesteel.randomizer_2;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;


public class Launcher extends FragmentActivity {


    final String TAG = "mLOGs";

    private TinyDB tinyDB;
    private ArrayList<String> randomizedArrayList;
    private int randomInt;
    CircularFillableLoaders circularFillableLoaders;
    LoadAndSetPicAsyncTask loadAndSetPicAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        circularFillableLoaders = (CircularFillableLoaders)findViewById(R.id.circularFillableLoaders);
        randomizedArrayList = new ArrayList<>();
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.icon2);

        loadAndSetPicAsyncTask = new LoadAndSetPicAsyncTask();
        loadBitmaps();
    }

    private void loadBitmaps(){
        tinyDB = new TinyDB(Launcher.this);
        switch (tinyDB.getString("MyResolution")){
            case "Resolution720":
                Log.d(TAG, "1280 720");
                loadBitmapHD(1280, 720);
                break;
            case "Resolution900":
                Log.d(TAG, "1600 900");
                loadBitmapHD(1600, 900);
                break;
            case "Resolution1080":
                Log.d(TAG, "1920 1080");
                loadBitmap2K(1920, 1080);
                break;
            case "Resolution1440":
                Log.d(TAG, "2560 1440");
                loadBitmap2K(2560, 1440);
                break;
        }
    }

    //Bitmap на 720-900px
    private void loadBitmapHD(int a, int b) {
        Picasso.with(Launcher.this)
                .load(randomizedStringHD())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.drawable.icon2)
                .resize(a, b)
                .into(target);
    }
    private String randomizedStringHD(){
        tinyDB = new TinyDB(Launcher.this);
        randomizedArrayList = tinyDB.getListString("HD_OptimizedArray");
        randomInt = (byte) Math.floor(Math.random()*randomizedArrayList.size());
        Log.d(TAG, "It's RandomInt " + randomInt);
        return randomizedArrayList.get(randomInt);
    }
    //Bitmap на 1080-1440px
    private void loadBitmap2K(int a, int b) {
        Picasso.with(Launcher.this)
                .load(randomizedString2K())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.drawable.icon2)
                .resize(a, b)
                .into(target);
    }
    private String randomizedString2K(){
        tinyDB = new TinyDB(Launcher.this);
        randomizedArrayList = tinyDB.getListString("2K_OptimizedArray");
        randomInt = (byte) Math.floor(Math.random()*randomizedArrayList.size());
        Log.d(TAG, "It's RandomInt " + randomInt);
        return randomizedArrayList.get(randomInt);
    }


    public Target target = new Target() {
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.d(TAG, "loaded");
            loadAndSetPicAsyncTask.execute(bitmap);
        }
    };



    class LoadAndSetPicAsyncTask extends AsyncTask<Bitmap, Integer,Void>{


        @Override
        protected Void doInBackground(Bitmap... params) {
            Log.d(TAG, "Clearing Cache");

            Bitmap bitmap = params[0];
            setBitmapWallpaper(bitmap);
            Glide.get(Launcher.this)
                    .clearDiskCache();
            return null;
        }


        public void setBitmapWallpaper(Bitmap bitmapWallpaper) {
            if (bitmapWallpaper != null) {

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(Launcher.this);
                Log.d(TAG, "Width "+ bitmapWallpaper.getWidth());
                Log.d(TAG, "Height "+ bitmapWallpaper.getHeight());
                Log.d(TAG, "setBitmap");
                try {
                    wallpaperManager.setBitmap(bitmapWallpaper);
                } catch (IOException e) {
                    Log.d(TAG, "Cannot");
                }
            }
        }
    }
}






