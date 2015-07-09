package com.freshmeat.whitesteel.randomizer_2.formats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freshmeat.whitesteel.randomizer_2.MainActivity;
import com.freshmeat.whitesteel.randomizer_2.R;

public class HD_Resol_btn extends Fragment {

    public static final String TAG="HD_Resol_btnTAG";
    Intent intent = new Intent();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.hd_choice_btn, container, false);
        final View button = view.findViewById(R.id.resolutionHD);



        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), HD_Choice.class);

                        startActivity(intent);
                       // finish();
                    }
                }
        );
        return view;
    }

//    private void finish() {
//        finish();
//    }
}
