package com.r400.ultra.free.rwallpapers.fragments.images;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.appodeal.ads.Appodeal;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.NonSwipeableViewPager;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.activity.SampleActivity;
import com.r400.ultra.free.rwallpapers.app.AppController;
import com.r400.ultra.free.rwallpapers.fragments.transitions.RotationPageTransformer;
import com.r400.ultra.free.rwallpapers.model.MyImage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Whitesteel on '.
 */
public class ImagePagerFragment extends Fragment {

    public static String TAG = "mLogs";
    private ArrayList<MyImage> myImages;
    private ArrayList<String> selectedGenresReady;
    private TinyDB tinyDB;
    Button button;

    private ImageAdapter mAdapter;
    int max = 0;
    NonSwipeableViewPager pager;

    String appKey = "72766faa040772c0f8516a57c6d71d532a4f03bf53d9a1cd";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_image_pager, container, false);
        Appodeal.disableLocationPermissionCheck();
        Appodeal.disableNetwork(getActivity(), "amazon_ads");

        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
        Appodeal.setTesting(false);
        pager = (NonSwipeableViewPager) rootView.findViewById(R.id.pager);
        button = (Button) rootView.findViewById(R.id.btnNEXT);


        tinyDB = new TinyDB(rootView.getContext());
        selectedGenresReady = tinyDB.getListString("ReadyGenres");
        myImages = new ArrayList<>();

        mAdapter = new ImageAdapter(getActivity(), myImages);
        pager.setAdapter(mAdapter);

        pager.setPageTransformer(true, new RotationPageTransformer(180));

        Log.d(TAG, " " + selectedGenresReady.size() + " " + max);
        //Три картинки сразу
        pager.setClipToPadding(false);
        pager.setPadding(70, 0, 70, 0);
        pager.setPageMargin(30);

        fetchImages();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(randomPage());
            }
        });
        Appodeal.show(getActivity(), Appodeal.BANNER_BOTTOM);
        return rootView;
    }

    public int randomPage(){
        int randomInt = 0;
        max = myImages.size();
        randomInt = (int) Math.floor(Math.random() * max);
        return randomInt;
    }

    @Override
    public void onResume() {
        super.onResume();
        pager.setCurrentItem(randomPage());
    }


    private void fetchImages(){
        myImages.clear();
        Log.d(TAG, "myImages");
        for(int i = 0; i < selectedGenresReady.size(); i++) {
            JsonArrayRequest req = new JsonArrayRequest(selectedGenresReady.get(i),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, "LOG response");
                            for (int j = 0; j < response.length(); j++) {
                                try {
                                    JSONObject object = response.getJSONObject(j);
                                    MyImage myImage = new MyImage();
                                    myImage.setCategory(object.getString("category"));
                                    myImage.setTags(object.getString("tags"));
                                    myImage.setResolution(object.getString("resolution"));
                                    myImage.setWeight(object.getString("weight"));

                                    JSONObject url = object.getJSONObject("url");
                                    myImage.setLow(url.getString("small"));
                                    myImage.setHigh(url.getString("large"));
                                    myImage.setTimestamp(object.getString("timestamp"));
                                    Log.d(TAG, "LOG " + myImage.getLow());
                                    myImages.add(myImage);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            AppController.getInstance().addToRequestQueue(req);
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private ArrayList<MyImage> myImages;
        private Context mContext;

        private TinyDB tinyDB;

        private LayoutInflater inflater;
        ImageView imageView;
        ProgressBar spinner;

        ImageAdapter(Context context, ArrayList<MyImage> myImages) {
            inflater = LayoutInflater.from(context);
            this.mContext = context;
            this.myImages = myImages;
            tinyDB = new TinyDB(context);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            pager.setCurrentItem(randomPage());//Рандом при старте
        }

        @Override
        public int getCount() {
            return myImages.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            imageView = (ImageView) imageLayout.findViewById(R.id.imgSmallFirst);
            spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

            Picasso.with(mContext)
                    .load(myImages.get(position).getLow())
                    .noPlaceholder()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_STORE)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SampleActivity.class);
                    tinyDB.putListImages("AllMyImages", myImages);
                    tinyDB.putInt("PositionMyImages", position);
                    Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}