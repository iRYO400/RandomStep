package com.r400.ultra.free.rwallpapers.activity;


import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.isseiaoki.simplecropview.CropImageView;
import com.r400.ultra.free.rwallpapers.R;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Whitesteel on 03.03.2016.
 */
public class CustomCropActivity extends AppCompatActivity {

    CropImageView cropImageView;
    Button cropBtn;
    TextView infoWidth;
    TextView infoHeight;
    TextView devInfoWidth;
    TextView devInfoHeight;
    CheckBox cbFitHeight;
    Bitmap bitmap;
    Intent intent;

    int devHeight = 0;
    int devWidth = 0;

    String appKey = "72766faa040772c0f8516a57c6d71d532a4f03bf53d9a1cd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image);


        Display display = this.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17){
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            devWidth = realMetrics.widthPixels;
            devHeight = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            //reflection for this weird in-between time
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                devWidth = (Integer) mGetRawW.invoke(display);
                devHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                //this may not be 100% accurate, but it's all we've got
                devWidth = display.getWidth();
                devHeight = display.getHeight();
                Log.e("Display Info", "Couldn't use reflection to get the real display metrics.");
            }

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            devWidth = display.getWidth();
            devHeight = display.getHeight();
        }

        initView();
        initPicasso();
        cropImageView.setCropMode(CropImageView.CropMode.FREE);
        devInfoWidth.setText("" + devWidth);
        devInfoHeight.setText("" + devHeight);

        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFitHeight.isChecked()) {
                    int new_Width = ((cropImageView.getCroppedBitmap().getWidth()*devHeight)/cropImageView.getCroppedBitmap().getHeight());
                    bitmap = Bitmap.createScaledBitmap(cropImageView.getCroppedBitmap(), new_Width, devHeight, false);
                    setBitmapWallpaper(bitmap);
                }
                else {
                    setBitmapWallpaper(cropImageView.getCroppedBitmap());
                }
            }
        });
    }


    public void setBitmapWallpaper(Bitmap bitmapWallpaper) {
        if (bitmapWallpaper != null) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(CustomCropActivity.this);
            try {
                wallpaperManager.setBitmap(bitmapWallpaper);
            } catch (IOException e) {

            }
        }
    }

    private void initPicasso(){
            Picasso.with(this)
                    .load(intent.getStringExtra("DataURL"))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .into(target);
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
            cropImageView.setImageBitmap(bitmap);
            infoWidth.setText("Width: " + bitmap.getWidth());
            infoHeight.setText("Height: " + bitmap.getHeight());
        }
    };
    private void initView(){
        intent = getIntent();
        cropImageView = (CropImageView)findViewById(R.id.cropImageView);
        cropBtn = (Button)findViewById(R.id.crop_button);
        infoWidth = (TextView) findViewById(R.id.imgWidthInfo);
        infoHeight = (TextView) findViewById(R.id.imgHeightInfo);
        devInfoWidth = (TextView) findViewById(R.id.devWidthInfo);
        devInfoHeight = (TextView) findViewById(R.id.devHeightInfo);
        cbFitHeight = (CheckBox) findViewById(R.id.cbHeight);

        Typeface keys = Typeface.createFromAsset(getAssets(), getString(R.string.inside_font));
        ViewGroup keyboardArea = (ViewGroup)findViewById(R.id.customCrop);
        setFont(keyboardArea, keys);
    }

    public void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView) {
                ((TextView)v).setTypeface(font);
            } else if(v instanceof ViewGroup) {
                setFont((ViewGroup) v, font);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }
        super.onBackPressed();
    }
}
