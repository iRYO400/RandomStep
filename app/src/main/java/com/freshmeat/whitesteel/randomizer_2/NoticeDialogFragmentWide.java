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

public class NoticeDialogFragmentWide extends DialogFragment {

    ArrayList mSelectedItems;
    final String LOG_TAG="mLOGs";
    int b;

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
        if(intent.hasExtra("800")) {
            Log.d(LOG_TAG, "800 chose in NoticeDialog");
            builder.setTitle("Sup")
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
                            // Send the positive button event back to the host activity
                            intent.putExtra("Ch800", b);
                            Log.d(LOG_TAG, "Wide genres chosen" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragmentWide.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragmentWide.this);
                        }
                    });
        }
        if(intent.hasExtra("1050")) {
            Log.d(LOG_TAG, "1050 chose in NoticeDialog");
            builder.setTitle("Sup")
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
                            // Send the positive button event back to the host activity
                            intent.putExtra("Ch1050", b);
                            Log.d(LOG_TAG, "Wide genres chosen" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragmentWide.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragmentWide.this);
                        }
                    });
        }
        if(intent.hasExtra("1200")) {
            Log.d(LOG_TAG, "1200 chose in NoticeDialog");
            builder.setTitle("Sup")
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
                            // Send the positive button event back to the host activity
                            intent.putExtra("Ch1200", b);
                            Log.d(LOG_TAG, "Wide genres chosen" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragmentWide.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragmentWide.this);
                        }
                    });
        }
        if(intent.hasExtra("1600")) {
            Log.d(LOG_TAG, "1600 chose in NoticeDialog");
            builder.setTitle("Sup")
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
                            // Send the positive button event back to the host activity
                            intent.putExtra("Ch1600", b);
                            Log.d(LOG_TAG, "Wide genres chosen" + mSelectedItems);
                            mListener.onDialogPositiveClick(NoticeDialogFragmentWide.this);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                            mListener.onDialogNegativeClick(NoticeDialogFragmentWide.this);
                        }
                    });
        }
        return builder.create();
    }
}