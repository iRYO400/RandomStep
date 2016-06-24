package com.r400.ultra.free.rwallpapers.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nvanbenschoten.motion.ParallaxImageView;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.app.AppController;
import com.r400.ultra.free.rwallpapers.fragments.images.Constants;
import com.r400.ultra.free.rwallpapers.model.MyGenre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SplashScreen extends Activity {

    private ArrayList<MyGenre> cgContent;
    private ArrayList<MyGenre> moviesContent;
    private ArrayList<MyGenre> worldContent;
    private ArrayList<MyGenre> craftsContent;
    private ParallaxImageView parallaxImageView;
    private TextView textView;
    private TinyDB tinyDB;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        parallaxImageView = (ParallaxImageView) findViewById(R.id.parallaxSplash);
        parallaxImageView.setImageResource(R.drawable.dark_splash);

        Typeface CF = Typeface.createFromAsset(getAssets(), getString(R.string.title_font));
        textView = (TextView) findViewById(R.id.splashTextView);
        ((TextView) findViewById(R.id.splashTextView)).setTypeface(CF);

        tinyDB = new TinyDB(SplashScreen.this);
        cgContent = new ArrayList<>();
        moviesContent = new ArrayList<>();
        worldContent = new ArrayList<>();
        craftsContent = new ArrayList<>();
        new PrefetchData().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        parallaxImageView.registerSensorManager();
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            cgContent.clear();
                JsonArrayRequest req1 = new JsonArrayRequest(Constants.CGANDART,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int j = 0; j < response.length(); j++) {
                                    try {
                                        JSONObject object = response.getJSONObject(j);
                                        MyGenre myGenre = new MyGenre();
                                        myGenre.setName(object.getString("name"));
                                        myGenre.setImage(object.getString("image"));
                                        myGenre.setInfo(object.getString("info"));
                                        cgContent.add(myGenre);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                tinyDB.putListGenres("cgContent", cgContent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
            AppController.getInstance().addToRequestQueue(req1);
            JsonArrayRequest req2 = new JsonArrayRequest(Constants.MOVIES,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int j = 0; j < response.length(); j++) {
                                try {
                                    JSONObject object = response.getJSONObject(j);
                                    MyGenre myGenre = new MyGenre();
                                    myGenre.setName(object.getString("name"));
                                    myGenre.setImage(object.getString("image"));
                                    myGenre.setInfo(object.getString("info"));
                                    moviesContent.add(myGenre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tinyDB.putListGenres("moviesContent", moviesContent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            AppController.getInstance().addToRequestQueue(req2);
            JsonArrayRequest req3 = new JsonArrayRequest(Constants.WORLD,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int j = 0; j < response.length(); j++) {
                                try {
                                    JSONObject object = response.getJSONObject(j);
                                    MyGenre myGenre = new MyGenre();
                                    myGenre.setName(object.getString("name"));
                                    myGenre.setImage(object.getString("image"));
                                    myGenre.setInfo(object.getString("info"));
                                    worldContent.add(myGenre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tinyDB.putListGenres("worldContent", worldContent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            AppController.getInstance().addToRequestQueue(req3);
            JsonArrayRequest req4 = new JsonArrayRequest(Constants.CRAFTS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int j = 0; j < response.length(); j++) {
                                try {
                                    JSONObject object = response.getJSONObject(j);
                                    MyGenre myGenre = new MyGenre();
                                    myGenre.setName(object.getString("name"));
                                    myGenre.setImage(object.getString("image"));
                                    myGenre.setInfo(object.getString("info"));
                                    craftsContent.add(myGenre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tinyDB.putListGenres("craftsContent", craftsContent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            AppController.getInstance().addToRequestQueue(req4);
            for(int i = 0; i < 100; i++){
                SystemClock.sleep(15);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
