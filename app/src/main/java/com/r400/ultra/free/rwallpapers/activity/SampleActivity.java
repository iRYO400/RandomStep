package com.r400.ultra.free.rwallpapers.activity;

import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nvanbenschoten.motion.ParallaxImageView;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.model.MyImage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Oleksii Shliama (https://github.com/shliama).
 */
public class SampleActivity extends FragmentActivity {

    private static final String TAG = "SampleActivity";
    private static final int DOWNLOAD_NOTIFICATION_ID_DONE = 911;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.jpg";

    private ArrayList<MyImage> myImages;
    private TinyDB tinyDB;
    private int position;
    ParallaxImageView parallaxImageView;
    TextView categoryInfo;
    TextView tagsInfo;
    TextView weightInfo;
    TextView imgResInfo;
    TextView devHeightInfo;
    TextView devWidthInfo;
    Button cropModeBtn;
    Button setWall;
    ImageView imgSmallSecond;

    int devWidth = 0;
    int devHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        tinyDB = new TinyDB(this);
        setubUI();
        getRealScreenSize();
        setupInfo();

        cropModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropActivity(myImages.get(position).getHigh());
            }
        });
        setWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(SampleActivity.this)
                        .load(myImages.get(position).getHigh())
//                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                        .into(target);
            }
        });
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int new_Width = ((bitmap.getWidth()*devHeight)/bitmap.getHeight());
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, new_Width, devHeight, false);
            setBitmapWallpaper(bitmap2);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private void setubUI(){
        parallaxImageView = (ParallaxImageView) findViewById(R.id.parallaxExtended);
        myImages = tinyDB.getListImages("AllMyImages", MyImage.class);
        position = tinyDB.getInt("PositionMyImages");
        imgSmallSecond = (ImageView) findViewById(R.id.imgSmallSecond);
        categoryInfo = (TextView) findViewById(R.id.categoryInfo);
        tagsInfo = (TextView) findViewById(R.id.tagsInfo);
        weightInfo = (TextView) findViewById(R.id.weightInfo);
        imgResInfo = (TextView) findViewById(R.id.imgResInfo);

        devHeightInfo = (TextView) findViewById(R.id.devHeightInfo);
        devWidthInfo = (TextView) findViewById(R.id.devWidthInfo);
        cropModeBtn = (Button) findViewById(R.id.cropMode);
        setWall = (Button) findViewById(R.id.setWall);

    }

    private void setupInfo() {
        Picasso.with(this)
                .load(myImages.get(position).getLow())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(imgSmallSecond);

        categoryInfo.setText(myImages.get(position).getCategory());
        tagsInfo.setText(myImages.get(position).getTags());
        weightInfo.setText(myImages.get(position).getWeight());
        imgResInfo.setText(myImages.get(position).getResolution());
        devWidthInfo.setText("" + devWidth);
        devHeightInfo.setText("" + devHeight);
        tinyDB.putInt("devHeight", devHeight);
        Typeface keys = Typeface.createFromAsset(getAssets(), getString(R.string.inside_font));
        ViewGroup keyboardArea = (ViewGroup)findViewById(R.id.advanced_info);
        setFont(keyboardArea, keys);
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

    public void setBitmapWallpaper(Bitmap bitmapWallpaper) {
        if (bitmapWallpaper != null) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(SampleActivity.this);
            try {
                wallpaperManager.setBitmap(bitmapWallpaper);
                showNotification();
            } catch (IOException e) {

            }
        }
    }

        private void showNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        NotificationCompat.Builder mNotification = new NotificationCompat.Builder(this);

        mNotification
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_image_saved_click_to_preview))
                .setTicker(getString(R.string.notification_image_saved))
                .setSmallIcon(R.drawable.ic_done)
                .setOngoing(false)
                .setAutoCancel(true);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(DOWNLOAD_NOTIFICATION_ID_DONE, mNotification.build());
    }

    private void getRealScreenSize(){
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull String link) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        UCrop uCrop = UCrop.of(Uri.parse(link), Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = advancedConfig(uCrop);
        uCrop.start(SampleActivity.this);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionQuality(100);
        options.setFreeStyleCropEnabled(true);
        options.setToolbarColor(getResources().getColor(R.color.blackFront));
        options.setStatusBarColor(getResources().getColor(R.color.blackBack));
        //Цвет активной иконки
        options.setActiveWidgetColor(getResources().getColor(R.color.textHoveredColor));
        //Тулбар текст иконки
        options.setToolbarWidgetColor(getResources().getColor(R.color.textHoveredColor));
        return uCrop.withOptions(options);
    }


    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            ResultActivity.startWithUri(SampleActivity.this, resultUri);
        } else {
            Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(SampleActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SampleActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
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