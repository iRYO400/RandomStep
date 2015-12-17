package com.freshmeat.whitesteel.randomizer_2;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;


public class Launcher extends FragmentActivity {


    final String TAG= "mLOGs";
    String[] array;
    int n;


    public String arrayZone() {

        array = getResources().getStringArray(R.array.array720);
        n = (int) Math.floor(Math.random() * array.length);
        Log.d(TAG, array[n]);
            if (getIntent().hasExtra("Ch720")) {
                array = getResources().getStringArray(R.array.array720);
                n = (int) Math.floor(Math.random() * array.length);
                Log.d(TAG, array[n]);

            }
            if (getIntent().hasExtra("Ch900")) {
                array = getResources().getStringArray(R.array.array900);
                n = (int) Math.floor(Math.random() * array.length);
                Log.d(TAG, array[n]);
            }
            if (getIntent().hasExtra("Ch1080")) {
                array = getResources().getStringArray(R.array.array1080);
                n = (int) Math.floor(Math.random() * array.length);
                Log.d(TAG, array[n]);
            }
            if (getIntent().hasExtra("Ch1440")) {
                array = getResources().getStringArray(R.array.array1440);
                n = (int) Math.floor(Math.random() * array.length);
                Log.d(TAG, array[n]);
            } else {
                Log.d(TAG, "Fail, repair it NOW!");
            }
            return array[n];
        }


    private void loadBitmap() {
        Picasso.with(this)
                .load(arrayZone())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(target);
        Log.d(TAG,"inTO TARGET");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getApplicationContext()==null){
             loadBitmap();
        }
        else {
            finish();
        }

    }


}
