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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.InternetConnection;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.fragments.genres.CGAdapter;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentCG;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentCrafts;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentMovies;
import com.r400.ultra.free.rwallpapers.fragments.genres.RecyclerViewFragmentWorld;
import com.r400.ultra.free.rwallpapers.fragments.ZoomOutPageTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CGAdapter.AdapterCallback {

    final String TAG = "mLOGs";
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    private ImageView imageView;

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";

    //https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
    private TinyDB tinyDB;

    private ArrayList<Genres> cgGenresArrayList;
    private ArrayList<Genres> moviesGenresArrayList;
    private ArrayList<Genres> worldGenresArrayList;
    private ArrayList<Genres> craftsGenresArrayList;
    private ArrayList<Genres> fullGenresArrayList;
    private ArrayList<String> toDeployArrayList;
    public Button btnLicense;
    public Button btnVk;
    public Button btnFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_portret);


        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        imageView = (ImageView) findViewById(R.id.backgroundImg);
        imageView.setBackgroundResource(randomBackground());
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
        });

        //Движущийся бэкграунд
        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int totalPages = mViewPager.getViewPager().getAdapter().getCount();
                float finalPercentage = ((position + positionOffset) * 700 / totalPages);
                imageView.setTranslationX(-finalPercentage);
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

        //Название разделов Selection Actualités Professionnel
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

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

        //RateApp
//        AppRate.with(this)
//                .setInstallDays(0) // default 10, 0 means install day.
//                .setLaunchTimes(0)
//                .setRemindInterval(2) // default 1
//                .setShowLaterButton(true) // default true
//                .setTitle(R.string.support_me)
//                .setMessage(R.string.support_message)
//                .setTextLater(R.string.support_later)
//                .setTextNever(R.string.support_no)
//                .setTextRateNow(R.string.support_now)
//                .monitor();
    }

    public void initArrayLists(){
        cgGenresArrayList = new ArrayList<>();
        moviesGenresArrayList = new ArrayList<>();
        worldGenresArrayList = new ArrayList<>();
        craftsGenresArrayList = new ArrayList<>();
        fullGenresArrayList = new ArrayList<>();
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
        int[] bcgroundArray = new int[17];
        int max = bcgroundArray.length;
        bcgroundArray[0] = R.drawable.background1;
        bcgroundArray[1] = R.drawable.background2;
        bcgroundArray[2] = R.drawable.background3;
        bcgroundArray[3] = R.drawable.background4;
        bcgroundArray[4] = R.drawable.background5;
        bcgroundArray[5] = R.drawable.background6;
        bcgroundArray[6] = R.drawable.background7;
        bcgroundArray[7] = R.drawable.background8;
        bcgroundArray[8] = R.drawable.background10;
        bcgroundArray[9] = R.drawable.background12;
        bcgroundArray[10] = R.drawable.background9;
        bcgroundArray[11] = R.drawable.background14;
        bcgroundArray[12] = R.drawable.background15;
        bcgroundArray[13] = R.drawable.background16;
        bcgroundArray[14] = R.drawable.background17;
        bcgroundArray[15] = R.drawable.background18;
        bcgroundArray[16] = R.drawable.background19;
        random = (int) Math.floor(Math.random()*max);
        return bcgroundArray[random];
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AppRate.showRateDialogIfMeetsConditions(this);
        tinyDB = new TinyDB(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started_main), false);
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
    }


    @Override
    public void updateChecked(CGAdapter cgAdapter) {
        if (!fullGenresArrayList.isEmpty()) {
            fullGenresArrayList.clear();
        }
        cgGenresArrayList = tinyDB.getListObject("listFromCGAdapter", Genres.class);
        moviesGenresArrayList = tinyDB.getListObject("listFromMoviesAdapter", Genres.class);
        worldGenresArrayList = tinyDB.getListObject("listFromWorldAdapter", Genres.class);
        craftsGenresArrayList = tinyDB.getListObject("listFromCraftsAdapter", Genres.class);

        fullGenresArrayList.addAll(cgGenresArrayList);
        fullGenresArrayList.addAll(moviesGenresArrayList);
        fullGenresArrayList.addAll(worldGenresArrayList);
        fullGenresArrayList.addAll(craftsGenresArrayList);

        tinyDB.putListGenres("fullGenresArrayList", fullGenresArrayList);
    }

    public void onClick(View view) {
        Intent intent;
        fullGenresArrayList = tinyDB.getListObject("fullGenresArrayList", Genres.class);
        switch (view.getId()) {
            case R.id.btnLaunch:
                for (int i = 0; i < fullGenresArrayList.size(); i++) {
                    Genres gr = fullGenresArrayList.get(i);
                    if (gr.isSelected()) {
                        toDeployArrayList.add(gr.getName());
                    }
                }
                tinyDB.putListString("toDeployArrayList", toDeployArrayList);
                intent = new Intent(MainActivity.this, Launcher.class);
                startActivity(intent);
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