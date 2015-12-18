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

    static NoticeDialogFragment newInstance(int num) {
        NoticeDialogFragment f = new NoticeDialogFragment();

        Bundle args = new Bundle();
        args.putInt("720", num);
        f.setArguments(args);
        return f;
    }

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

    //  String extra=getIntent().hasExtra("720");

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedItems = new ArrayList<>();
        final Intent intent = getActivity().getIntent();


        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (intent.hasExtra("720")) {
            Log.d(LOG_TAG, "first");
            builder.setTitle("Hello1")

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
                            intent.putExtra("Ch720", b);
                            Log.d(LOG_TAG, "Has been defused" + mSelectedItems);
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
        if (intent.hasExtra("900")) {
            Log.d(LOG_TAG, "second");
            builder.setTitle("Hello2")

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
                            intent.putExtra("Ch900", b);
                            Log.d(LOG_TAG, "Has been defused" + mSelectedItems);
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
        if (intent.hasExtra("1080")) {
            Log.d(LOG_TAG, "third");
            builder.setTitle("Hello3")

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
                            intent.putExtra("Ch1080", b);
                            Log.d(LOG_TAG, "Has been defused" + mSelectedItems);
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
        if (intent.hasExtra("1440")) {
            Log.d(LOG_TAG, "fourth");
            builder.setTitle("Hello4")

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
                            intent.putExtra("Ch1440", b);
                            Log.d(LOG_TAG, "Has been defused" + mSelectedItems);
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