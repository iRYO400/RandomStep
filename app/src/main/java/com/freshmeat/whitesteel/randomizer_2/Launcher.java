package com.freshmeat.whitesteel.randomizer_2;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Launcher extends FragmentActivity {

    final String TAG= "mLOGs";
    protected String[] array;
    protected int randomNumber = 2;
    TinyDB tinyDB;
    ArrayList<String> arrayListGenres;
    ArrayList<String> arrayListFull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayListGenres = new ArrayList<String>();
        arrayListFull = new ArrayList<String>();
        loadBitmaps();
        finish();
        Log.d(TAG, "Succesfully onCreate method");
    }


    private void loadBitmaps(){
        switch (tinyDB.getString("MyResolution")){
            case "Resolution720":
                break;
            case "Resolution900":
                break;
            case "Resolution1080":
                break;
            case "Resolution1440":
                break;
        }

        loadBitmap720();
    }

    private void loadBitmap720() {

        Picasso.with(this)
                .load(array720())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"Loading 720p");
    }
    public String array720(){

        tinyDB = new TinyDB(this);
        arrayListGenres = tinyDB.getListString("MyGenres");
        for(int i = 0; i < arrayListGenres.size(); i++){
            switch (arrayListGenres.get(i)) {
                case "CG":
                    ArrayList<String> arrayListCG = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.CG)));
                    arrayListFull.addAll(arrayListCG);
                    break;
                case "Games":
                    ArrayList<String> arrayListGames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Games)));
                    arrayListFull.addAll(arrayListGames);
                    break;
                case "World":
                    ArrayList<String> arrayListWorld = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.World)));
                    arrayListFull.addAll(arrayListWorld);
                    break;
                case "Sport":
                    ArrayList<String> arrayListSport = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Sport)));
                    arrayListFull.addAll(arrayListSport);
                    break;
                case "Auto":
                    ArrayList<String> arrayListAuto = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Auto)));
                    arrayListFull.addAll(arrayListAuto);
                    break;
            }
        }
        Log.d(TAG, " array720 " + arrayListGenres + " " + arrayListFull);
        //randomNumber = (byte) Math.random()arrayListFull.size();
        Log.d(TAG, arrayListFull.get(randomNumber));
        return arrayListFull.get(randomNumber);
    }

    private void loadBitmap900() {
        Picasso.with(this)
                .load(array900())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"Loading 900p");
    }
    public String array900(){
        array = getResources().getStringArray(R.array.array900);
        randomNumber = (byte) Math.random()*array.length;
        Log.d(TAG, array[randomNumber]);
        return array[randomNumber];
    }

    private void loadBitmap1080() {
        Picasso.with(this)
                .load(array1080())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"Loading 1080p");
    }
    public String array1080(){
        array = getResources().getStringArray(R.array.array1080);
        randomNumber = (byte) Math.random()*array.length;
        Log.d(TAG, array[randomNumber]);
        return array[randomNumber];
    }

    private void loadBitmap1440() {
        Picasso.with(this)
                .load(array1440())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"Loading 1080p");
    }
    public String array1440(){
        array = getResources().getStringArray(R.array.array1440);
        randomNumber = (byte) Math.random()*array.length;
        Log.d(TAG, array[randomNumber]);
        return array[randomNumber];
    }

    public String arrayZone() {
        array = getResources().getStringArray(R.array.array720);
        randomNumber = (int) Math.floor(Math.random() * array.length);
        Log.d(TAG, array[randomNumber]);
            return array[randomNumber];
        }
    private void loadBitmap() {
        Picasso.with(this)
                .load(arrayZone())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"Default Array");
    }


    private Target target = new Target() {

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.d(TAG, "fail");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (placeHolderDrawable != null) {
                Log.d(TAG, "prepare");
            }
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.d(TAG, "loaded");
            setBitmapWallpaper(bitmap);
            Log.d(TAG, "Width "+ bitmap.getWidth());
            Log.d(TAG, "Height "+ bitmap.getHeight());
        }
    };


    public void setBitmapWallpaper(Bitmap bitmapWallpaper) {
        if(target!=null) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            Log.d(TAG, "setBitmap");
            try {
                wallpaperManager.setBitmap(bitmapWallpaper);
            } catch (IOException e) {
                Log.d(TAG, "Cannot");
            }
        }
    }
}
