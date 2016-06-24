package com.r400.ultra.free.rwallpapers.fragments.genres;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.model.MyGenre;

import java.util.ArrayList;

/**
 * Created by Whitesteel on 29.02.2016.
 */
public class RecyclerViewFragmentMovies extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TinyDB tinyDB;
    private ArrayList<MyGenre> mContentItems;


    public static RecyclerViewFragmentMovies newInstance() {
        return new RecyclerViewFragmentMovies();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tinyDB = new TinyDB(getContext());
        mContentItems = new ArrayList<>();
        mContentItems = tinyDB.getListGenres("moviesContent", MyGenre.class);
//        fetchImages();
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

//    private void fetchImages(){
//        mContentItems.clear();
//        JsonArrayRequest req = new JsonArrayRequest(Constants.MOVIES,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for (int j = 0; j < response.length(); j++) {
//                            try {
//                                JSONObject object = response.getJSONObject(j);
//                                MyGenre myGenre = new MyGenre();
//                                myGenre.setName(object.getString("name"));
//                                myGenre.setImage(object.getString("image"));
//                                myGenre.setInfo(object.getString("info"));
//                                mContentItems.add(myGenre);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        AppController.getInstance().addToRequestQueue(req);
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new CGAdapter(getActivity(),mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        {
            mAdapter.notifyDataSetChanged();
        }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}

