package com.freshmeat.whitesteel.randomizer_2;

import android.app.WallpaperManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;


public class Launcher extends FragmentActivity {


    final String TAG= "mLOGs";

    protected String[] array;
    protected int randomNumber;

    MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadBitmaps();
        finish();
        Log.d(TAG, "Succesfully onCreate method");
    }


    private void loadBitmaps(){
        if(getIntent().hasExtra("Ch720")){
            loadBitmap720();
        } else if(getIntent().hasExtra("Ch900")){
            loadBitmap900();
        } else if (getIntent().hasExtra("Ch1080")){
            loadBitmap1080();
        }else if(getIntent().hasExtra("Ch1440")){
            loadBitmap1440();
        }else {
            loadBitmap();
        }
    }

    private void loadBitmap720() {

        Picasso.with(this)
                .load(array720())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"Loading 720p");
    }
    public String array720(){
        array = getResources().getStringArray(R.array.array720);
        randomNumber = (byte) Math.random()*array.length;
        Log.d(TAG, array[randomNumber]);
        return array[randomNumber];
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
