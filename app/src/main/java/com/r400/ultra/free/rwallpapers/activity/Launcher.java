package com.r400.ultra.free.rwallpapers.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.InternetConnection;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Arrays;

import it.michelelacorte.elasticprogressbar.ElasticDownloadView;
import it.michelelacorte.elasticprogressbar.OptionView;



public class Launcher extends AppCompatActivity {

    private TinyDB tinyDB;
    private ArrayList<String> selectedGenresReady;
    private ArrayList<String> selectedGenresArray;

    private Intent intent;
    private String urlID;
    private int randomInt;
    private ElasticDownloadView mElasticDownloadView;
    private progressBarAsynkTask progressBarAsynkTask;
    private LoadAndSetPicAsyncTask loadAndSetPicAsyncTask;
    private int progressElastic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OptionView.noBackground = true;
        setContentView(R.layout.activity_launcher);
        tinyDB = new TinyDB(Launcher.this);
        mElasticDownloadView = (ElasticDownloadView)findViewById(R.id.elastic_download_view);

        TextView textTipLauncher = (TextView) findViewById(R.id.textTipLauncher);
        Typeface keys = Typeface.createFromAsset(getAssets(), getString(R.string.title_font));
        ViewGroup keyboardArea = (ViewGroup)findViewById(R.id.launcher_root);
        setFont(keyboardArea, keys);

        textTipLauncher.setText(randomTip());

        //ElasticLoader & BitmapLoader
        progressElastic = 0;
        progressBarAsynkTask = new progressBarAsynkTask();
        loadAndSetPicAsyncTask = new LoadAndSetPicAsyncTask();
        mElasticDownloadView.startIntro();
        selectedGenresArray = new ArrayList<>();
        selectedGenresReady = new ArrayList<>();
        getSelectedGenres();
    }

    public int randomTip(){
        int[] array = new int[2];
        array[0] = R.string.tip_string2;
        array[1] = R.string.tip_string3;
        int max = 2;
        int randomTip = (int) Math.floor(Math.random() * max);
        return array[randomTip];
    }

    private void getSelectedGenres(){
        selectedGenresArray = tinyDB.getListString("toDeployArrayList");
        if(!selectedGenresArray.isEmpty()) {
            if (InternetConnection.internetConnectionAvailable(10000)) {
                progressBarAsynkTask.execute();
                loadSelectedGenres();
            } else {
                mElasticDownloadView.fail();
                Toast.makeText(this, "No Internet connection.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            mElasticDownloadView.fail();
            Toast.makeText(this, "Your Favorite List is empty ", Toast.LENGTH_LONG).show();
        }

    }

    private void loadSelectedGenres(){
        for(int i = 0; i < selectedGenresArray.size(); i++) {
            switch (selectedGenresArray.get(i)) {
                case "3D and Abstract":
                    ArrayList<String> arrayListCG = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.testArray)));
                    selectedGenresReady.addAll(arrayListCG);
//                    arrayListCG = null;
                    break;
                case "Fantasy":
                    ArrayList<String> arrayListFantasy = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Fantasy)));
                    selectedGenresReady.addAll(arrayListFantasy);
//                    arrayListFantasy = null;

                    break;
                case "Gaming":
                    ArrayList<String> arrayListGaming = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Gaming)));
                    selectedGenresReady.addAll(arrayListGaming);
//                    arrayListGaming = null;
                    break;
                case "Vector":
                    ArrayList<String> arrayListVector = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Vector)));
                    selectedGenresReady.addAll(arrayListVector);
//                    arrayListVector = null;
                    break;
                case "Cartoons":
                    ArrayList<String> arrayListCartoons = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Cartoons)));
                    selectedGenresReady.addAll(arrayListCartoons);
//                    arrayListCartoons = null;
                    break;
                case "Celebrities":
                    ArrayList<String> arrayListCelebrities = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Celebrities)));
                    selectedGenresReady.addAll(arrayListCelebrities);
//                    arrayListCelebrities = null;
                    break;
                case "Films":
                    ArrayList<String> arrayListFilms = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Films)));
                    selectedGenresReady.addAll(arrayListFilms);
//                    arrayListFilms = null;
                    break;
                case "Animals":
                    ArrayList<String> arrayListWorld = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Animals)));
                    selectedGenresReady.addAll(arrayListWorld);
//                    arrayListWorld = null;
                    break;
                case "Cities":
                    ArrayList<String> arrayListCities = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Cities)));
                    selectedGenresReady.addAll(arrayListCities);
//                    arrayListCities = null;
                    break;
                case "Nature":
                    ArrayList<String> arrayListNature = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Nature)));
                    selectedGenresReady.addAll(arrayListNature);
//                    arrayListNature = null;
                    break;
                case "Seasons":
                    ArrayList<String> arrayListSeasons = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Seasons)));
                    selectedGenresReady.addAll(arrayListSeasons);
//                    arrayListSeasons = null;
                    break;
                case "Space":
                    ArrayList<String> arrayListSpace = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Space)));
                    selectedGenresReady.addAll(arrayListSpace);
//                    arrayListSpace = null;
                    break;
                case "Aircraft":
                    ArrayList<String> arrayListAircraft = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Aircraft)));
                    selectedGenresReady.addAll(arrayListAircraft);
//                    arrayListAircraft = null;
                    break;
                case "Bikes":
                    ArrayList<String> arrayListBikes = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Bikes)));
                    selectedGenresReady.addAll(arrayListBikes);
//                    arrayListBikes = null;
                    break;
                case "Cars":
                    ArrayList<String> arrayListCars = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Cars)));
                    selectedGenresReady.addAll(arrayListCars);
//                    arrayListCars = null;
                    break;
                case "Other":
                    ArrayList<String> arrayListOther = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Cars)));
                    selectedGenresReady.addAll(arrayListOther);
//                    arrayListOther = null;
                    break;
            }
        }
        loadBitmap();
    }
    private void loadBitmap() {
        Picasso.with(Launcher.this)
                .load(randomizedString2K())
                .into(target);
    }

    private String randomizedString2K(){
        int max = 0;
        max = selectedGenresReady.size();
        randomInt = (int) Math.floor(Math.random() * max);
        if(randomInt == tinyDB.getInt("randomNumber")){
            if(randomInt==0) randomInt++;
            else if(randomInt==max) randomInt--;
            else randomInt++;
        }
        tinyDB.putInt("randomNumber", randomInt);
        urlID = selectedGenresReady.get(randomInt);
        return selectedGenresReady.get(randomInt);
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
            loadAndSetPicAsyncTask.execute();
        }
    };

    //LoadBitmap AsynkTask
    class LoadAndSetPicAsyncTask extends AsyncTask<Bitmap, Integer,Void>{

        @Override
        protected Void doInBackground(Bitmap... params) {
            selectedGenresArray.clear();
            selectedGenresArray = null;
            selectedGenresReady.clear();
            selectedGenresReady = null;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            intent = new Intent(Launcher.this, CustomCropActivity.class);
            intent.putExtra("DataURL", urlID);
            startActivity(intent);
        }
    }

    class progressBarAsynkTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            while (progressElastic < 100) {
                progressElastic++;
                publishProgress(progressElastic);
                SystemClock.sleep(50);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mElasticDownloadView.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mElasticDownloadView.success();
        }
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
}






