package com.r400.ultra.free.rwallpapers.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r400.ultra.free.rwallpapers.R;

public class LicenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        Typeface CF = Typeface.createFromAsset(getAssets(), getString(R.string.title_font));
        ((TextView) findViewById(R.id.licenceAppName)).setTypeface(CF);

        Typeface keys = Typeface.createFromAsset(getAssets(), getString(R.string.inside_font));
        ViewGroup keyboardArea = (ViewGroup)findViewById(R.id.licenceLayout);
        setFont(keyboardArea, keys);
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
