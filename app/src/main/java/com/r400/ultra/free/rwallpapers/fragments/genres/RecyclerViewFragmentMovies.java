package com.r400.ultra.free.rwallpapers.fragments.genres;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.activity.Genres;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Whitesteel on 29.02.2016.
 */
public class RecyclerViewFragmentMovies extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private boolean isOpen;
    private int position;
    private Rect startBounds;
    private Rect finalBounds;
    private ScrollView materialViewPager;

    private ImageView mImageView;
    private TextView titleTextView, subTitleTextView;
    private ImageButton btnLike;
    private View tintView;
    private View mBackgroundView, mContainer;

    private static final int SHORT_ANIMATION_DURATION = 200;
    private static final int DELAY_ANIMATION_DURATION = 40;
    private static final int LIKE_BTN_ANIMATION_DURATION = 120;

    private ArrayList<Genres> mContentItems;

    private ArrayList<String> mGenresContent;

    public static RecyclerViewFragmentMovies newInstance() {
        return new RecyclerViewFragmentMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ArrayList<Integer> array_image = new ArrayList<Integer>();
        array_image.add(R.drawable.movies1);
        array_image.add(R.drawable.movies2);
        array_image.add(R.drawable.movies3);

        mGenresContent = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Movies)));
        mContentItems = new ArrayList<Genres>();
        for (int i = 0; i < mGenresContent.size(); i++) {
            Genres gr = new Genres(mGenresContent.get(i), false, array_image.get(i));
            mContentItems.add(gr);
        }

        mAdapter = new RecyclerViewMaterialAdapter(new CGAdapter(getActivity(),mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        {
            mAdapter.notifyDataSetChanged();
        }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }



    private void initViews(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mBackgroundView = view.findViewById(R.id.backgroundView);
        materialViewPager = (ScrollView)view.findViewById(R.id.contentLayout);
        titleTextView = (TextView) view.findViewById(R.id.title);
        mContainer = view.findViewById(R.id.container);

        tintView = view.findViewById(R.id.tintViewFront);

        mImageView = (ImageView) view.findViewById(R.id.imageView);
        titleTextView = (TextView) view.findViewById(R.id.title);
        subTitleTextView = (TextView) view.findViewById(R.id.subTitle);
    }

}

