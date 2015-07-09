package com.freshmeat.whitesteel.randomizer_2.genres;


import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.freshmeat.whitesteel.randomizer_2.NoticeDialogFragment;
import com.freshmeat.whitesteel.randomizer_2.R;

import java.util.ArrayList;


public class GenresHD extends DialogFragment {

    public static final String TAG="HD_Genre_TAG";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.genres_hd_btn, container, false);
        return view;
    }
 /*   private boolean isModal = false;

    public static GenresHD newInstance()
    {
        GenresHD frag = new GenresHD();
        frag.isModal = true; // WHEN FRAGMENT IS CALLED AS A DIALOG SET FLAG
        return frag;
    }


    public interface GenresHDListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
/*
    GenresHDListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (GenresHDListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement GenresHDListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(isModal){
            return super.onCreateView(inflater, container,savedInstanceState);
        }
        else {
            View view = inflater.inflate(R.layout.genres_hd_btn, container, false);

            return view;
        }
    }
*/

/*
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Integer> genreStrings = new ArrayList<>();

/*        listViewHD.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.GenresString,
                android.R.layout.simple_list_item_multiple_choice);
        listViewHD.setAdapter(adapter);

        GenresString = getResources().getStringArray(R.array.GenresString);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.genres_hd_btn, null);
        builder.setView(view);
        builder.setTitle(R.string.genres_pick)
                .setMultiChoiceItems(R.array.GenresString, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            genreStrings.add(which);
                        }
                        else if(genreStrings.contains(which)){
                            genreStrings.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
//                        mListener.onDialogPositiveClick(GenresHD.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
 //                       mListener.onDialogNegativeClick(GenresHD.this);
                    }
                });

        return builder.create();
    }
*/




/*
        final View button = view.findViewById(R.id.genres_go_hd_btn);
        dialogFragment = new Dialog();
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.genres_go_hd_btn:
                                dialogFragment.show(getFragmentManager(), "dialogFragment");
                                break;
                        }

                    }
                }
        );
        return view;
    }
*/
}