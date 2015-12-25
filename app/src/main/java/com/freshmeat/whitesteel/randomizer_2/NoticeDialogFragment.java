package com.freshmeat.whitesteel.randomizer_2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.ArrayList;

public class NoticeDialogFragment extends DialogFragment {


    ArrayList mSelectedItems;
    final String LOG_TAG = "mLOGs";
    int b;

    MainActivity mainActivity = new MainActivity();
    Launcher launcher= new Launcher();

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedItems = new ArrayList<>();
        final Intent intent = getActivity().getIntent();


        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (intent.hasExtra("720")||intent.hasExtra("Genre720")) {
            Log.d(LOG_TAG, "first");
            builder.setTitle("Genres in 720p")
                    .setMultiChoiceItems(R.array.GenresString, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                            if (isChecked) {
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Отправка ArrayList в MainActivity
                            mainActivity.makeGenreArray(mSelectedItems);
                            Log.d(LOG_TAG, "Has been defused 720 " + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                        }
                    });

        }
        if (intent.hasExtra("900")||intent.hasExtra("Genre900")) {
            Log.d(LOG_TAG, "second");
            builder.setTitle("Genres in 900p")

                    .setMultiChoiceItems(R.array.GenresString, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                            if (isChecked) {
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Отправка ArrayList в MainActivity
                            mainActivity.makeGenreArray(mSelectedItems);
                            Log.d(LOG_TAG, "Has been defused 900" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity

                            mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                        }
                    });

        }
        if (intent.hasExtra("1080")||intent.hasExtra("Genre1080")) {
            Log.d(LOG_TAG, "third");
            builder.setTitle("Genres in 1080p")
                    .setMultiChoiceItems(R.array.GenresString, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                            if (isChecked) {
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Отправка ArrayList в MainActivity
                            mainActivity.makeGenreArray(mSelectedItems);
                            Log.d(LOG_TAG, "Has been defused 1080" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                        }
                    });

        }
        if (intent.hasExtra("1440")||intent.hasExtra("Genre1440")) {
            Log.d(LOG_TAG, "fourth");
            builder.setTitle("Genres in 1440p")
                    .setMultiChoiceItems(R.array.GenresString, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                            if (isChecked) {
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Отправка ArrayList в MainActivity
                            mainActivity.makeGenreArray(mSelectedItems);
                            Log.d(LOG_TAG, "Has been defused 1440" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                        }
                    });

        }
        return builder.create();



    }
}