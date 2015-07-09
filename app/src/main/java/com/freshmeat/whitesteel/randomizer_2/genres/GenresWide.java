package com.freshmeat.whitesteel.randomizer_2.genres;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freshmeat.whitesteel.randomizer_2.R;


public class GenresWide extends DialogFragment {

    public static final String TAG="Wide_Genre_TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.genres_wide_btn, container, false);
        return view;
    }
}