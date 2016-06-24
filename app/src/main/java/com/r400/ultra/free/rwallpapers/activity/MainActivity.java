package com.r400.ultra.free.rwallpapers.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.nvanbenschoten.motion.ParallaxImageView;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.InternetConnection;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.fragments.genres.CGAdapter;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentCG;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentCrafts;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentMovies;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentWorld;
import com.r400.ultra.free.rwallpapers.fragments.transitions.ZoomOutPageTransformer;
import com.r400.ultra.free.rwallpapers.model.MyGenre;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CGAdapter.AdapterCallback {


    private MaterialViewPager mViewPager;
    ParallaxImageView parallaxImageView;

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";

    //https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
    private TinyDB tinyDB;

    private ArrayList<MyGenre> cgMyGenreArrayList;
    private ArrayList<MyGenre> moviesMyGenreArrayList;
    private ArrayList<MyGenre> worldMyGenreArrayList;
    private ArrayList<MyGenre> craftsMyGenreArrayList;
    private ArrayList<MyGenre> fullMyGenreArrayList;
    private ArrayList<String> toDeployArrayList;
    public Button btnLicense;
    public Button btnVk;
    public Button btnFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_portret);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        parallaxImageView = (ParallaxImageView) findViewById(R.id.parallaxImgMain);
        btnFB = (Button) findViewById(R.id.btnFB);
        btnVk = (Button) findViewById(R.id.btnVk);
        btnLicense = (Button) findViewById(R.id.btnLicense);

        initArrayLists();

        //Header кастомизация Header'a
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                if (InternetConnection.internetConnectionAvailable(10000)) {
                    switch (page) {
                        case 0:
                            return HeaderDesign.fromColorAndUrl(
                                    Color.BLACK,
                                    HeaderDesign(0));
                        case 1:
                            return HeaderDesign.fromColorAndUrl(
                                    Color.BLACK,
                                    HeaderDesign(1));
                        case 2:
                            return HeaderDesign.fromColorAndUrl(
                                    Color.BLACK,
                                    HeaderDesign(2));
                        case 3:
                            return HeaderDesign.fromColorAndUrl(
                                    Color.BLACK,
                                    HeaderDesign(3));
                    }
                }
                //execute others actions if needed (ex : modify your header logo)
                return null;
            }
        });

        //Внутренности разделов
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return RecyclerViewFragmentCG.newInstance();
                    case 1:
                        return RecyclerViewFragmentMovies.newInstance();
                    case 2:
                        return RecyclerViewFragmentWorld.newInstance();
                    default:
                        return RecyclerViewFragmentCrafts.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "CG and Art";
                    case 1:
                        return "Movies";
                    case 2:
                        return "World";
                    case 3:
                        return "Crafts";
                }
                return "";
            }

            @Override
            public float getPageWidth(int position) {

                return 1f;
            }
        });


        //Движущийся бэкграунд
        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int totalPages = mViewPager.getViewPager().getAdapter().getCount();
                float finalPercentage = ((position + positionOffset) * 700 / totalPages);
                parallaxImageView.setTranslationX(-finalPercentage);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Анимация ViewPager'a
        mViewPager.getViewPager().setPageTransformer(true, new ZoomOutPageTransformer());

//        mViewPager.getViewPager().setPageMargin(20);

        //Название разделов Selection Actualités Professionnel
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        //Шрифт
        Typeface keys = Typeface.createFromAsset(getAssets(), getString(R.string.inside_font));
        ViewGroup keyboardArea = (ViewGroup)findViewById(R.id.drawer_layout);
        setFont(keyboardArea, keys);

        btnVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(MainActivity.this, "https://vk.com/ultra_wallpapers");
            }
        });
        btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(MainActivity.this, "https://www.facebook.com/Ultra-HD-R-Wallpapers-2K-4K-8K-541743942677720/");
            }
        });
        btnLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LicenceActivity.class));
            }
        });

        AppRate.with(this)
                .setInstallDays(3) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .monitor();
    }

    public void initArrayLists(){
        cgMyGenreArrayList = new ArrayList<>();
        moviesMyGenreArrayList = new ArrayList<>();
        worldMyGenreArrayList = new ArrayList<>();
        craftsMyGenreArrayList = new ArrayList<>();
        fullMyGenreArrayList = new ArrayList<>();
        toDeployArrayList = new ArrayList<>();
    }

    //Random Header
    public String HeaderDesign(int Num){
        int random = 0;
            int max = 3;
            switch (Num) {
                case 0:
                    ArrayList<String> headerDesignCG = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.headerDesignCG)));
                    random = (int) Math.floor(Math.random() * max);
                    return headerDesignCG.get(random);
                case 1:
                    ArrayList<String> headerDesignMovies = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.headerDesignMovies)));
                    random = (int) Math.floor(Math.random() * max);
                    return headerDesignMovies.get(random);
                case 2:
                    ArrayList<String> headerDesignWorld = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.headerDesignWorld)));
                    random = (int) Math.floor(Math.random() * max);
                    return headerDesignWorld.get(random);
                case 3:
                    ArrayList<String> headerDesignCrafts = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.headerDesignCrafts)));
                    random = (int) Math.floor(Math.random() * max);
                    return headerDesignCrafts.get(random);
            }
        return "";
    }

    //Random Background
    public int randomBackground(){
        int random = 0;
        int[] bcgroundArray = new int[20];
        int max = bcgroundArray.length;
        bcgroundArray[0] = R.drawable.background2;
        bcgroundArray[1] = R.drawable.background3;
        bcgroundArray[2] = R.drawable.background5;
        bcgroundArray[3] = R.drawable.background6;
        bcgroundArray[4] = R.drawable.background7;
        bcgroundArray[5] = R.drawable.background8;
        bcgroundArray[6] = R.drawable.background10;
        bcgroundArray[7] = R.drawable.background12;
        bcgroundArray[8] = R.drawable.background9;
        bcgroundArray[9] = R.drawable.background15;
        bcgroundArray[10] = R.drawable.background16;
        bcgroundArray[11] = R.drawable.background18;
        bcgroundArray[12] = R.drawable.background19;
        bcgroundArray[13] = R.drawable.background20;
        bcgroundArray[14] = R.drawable.background21;
        bcgroundArray[15] = R.drawable.background22;
        bcgroundArray[16] = R.drawable.background23;
        bcgroundArray[17] = R.drawable.background24;
        bcgroundArray[18] = R.drawable.background25;
        bcgroundArray[19] = R.drawable.background26;
        random = (int) Math.floor(Math.random()*max);
        return bcgroundArray[random];
    }

    @Override
    protected void onResume() {
        super.onResume();
        tinyDB = new TinyDB(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started_main), false);
        parallaxImageView.registerSensorManager();
        parallaxImageView.setImageResource(randomBackground());
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started_main), Boolean.TRUE);
            edit.commit();
            new AlertDialog.Builder(this)
                    .setTitle(R.string.help1)
                    .setMessage(R.string.helpTip)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
        AppRate.showRateDialogIfMeetsConditions(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        parallaxImageView.unregisterSensorManager();
    }

    @Override
    public void updateChecked(CGAdapter cgAdapter) {
        if (!fullMyGenreArrayList.isEmpty()) {
            fullMyGenreArrayList.clear();
        }
        cgMyGenreArrayList = tinyDB.getListGenres("listFromCGAdapter", MyGenre.class);
        moviesMyGenreArrayList = tinyDB.getListGenres("listFromMoviesAdapter", MyGenre.class);
        worldMyGenreArrayList = tinyDB.getListGenres("listFromWorldAdapter", MyGenre.class);
        craftsMyGenreArrayList = tinyDB.getListGenres("listFromCraftsAdapter", MyGenre.class);

        fullMyGenreArrayList.addAll(cgMyGenreArrayList);
        fullMyGenreArrayList.addAll(moviesMyGenreArrayList);
        fullMyGenreArrayList.addAll(worldMyGenreArrayList);
        fullMyGenreArrayList.addAll(craftsMyGenreArrayList);

        tinyDB.putListGenres("fullMyGenreArrayList", fullMyGenreArrayList);
    }

    public void onClick(View view) {
        Intent intent;
        if (!toDeployArrayList.isEmpty() || !fullMyGenreArrayList.isEmpty()) {
            toDeployArrayList.clear();
            fullMyGenreArrayList.clear();
        }
        fullMyGenreArrayList = tinyDB.getListGenres("fullMyGenreArrayList", MyGenre.class);
        for (int i = 0; i < fullMyGenreArrayList.size(); i++) {
            MyGenre gr = fullMyGenreArrayList.get(i);
            if (gr.isSelected()) {
                toDeployArrayList.add(gr.getName());
            }
        }

        switch (view.getId()) {
            case R.id.btnLaunch:
                if(!toDeployArrayList.isEmpty()) {
                    if (InternetConnection.internetConnectionAvailable(10000)) {
                        tinyDB.putListString("toDeployArrayList", toDeployArrayList);
                        intent = new Intent(MainActivity.this, ChoosenViewPager.class);
                        Toast.makeText(this, "Deploy " + toDeployArrayList.size(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "No Internet connection.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this, "Your Favorite List is empty ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    public static void openLink(Activity activity, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);

        if(resInfo.isEmpty())return;

        for(ResolveInfo info: resInfo){
            if(info.activityInfo ==null)
                continue;
            if(VK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                || FACEBOOK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)){
                intent.setPackage(info.activityInfo.packageName);
                break;
            }
        }
        activity.startActivity(intent);
    }

// Sets the font on all TextViews in the ViewGroup.
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