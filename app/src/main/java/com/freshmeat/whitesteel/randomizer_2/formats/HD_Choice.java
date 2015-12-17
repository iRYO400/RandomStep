package com.freshmeat.whitesteel.randomizer_2.formats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.freshmeat.whitesteel.randomizer_2.MainActivity;
import com.freshmeat.whitesteel.randomizer_2.R;
import com.squareup.picasso.Picasso;

public class HD_Choice extends Activity implements View.OnClickListener {

    final String LOG_TAG = "mLOGs";
    ListView listViewHD;
    String[] HD;
    int a;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_resolutions);


        listViewHD = (ListView) findViewById(R.id.listViewHD);
        listViewHD.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.HD,
                android.R.layout.simple_list_item_single_choice);
        listViewHD.setAdapter(adapter);

        button = (Button) findViewById(R.id.btnOK1);
        button.setOnClickListener(this);
        HD = getResources().getStringArray(R.array.HD);

    }


    @Override
    public void onClick(View view) {
        if(listViewHD.getCheckedItemPosition()!=-1){
            button.setEnabled(true);

        Intent intent = new Intent(this, MainActivity.class);
        switch (listViewHD.getCheckedItemPosition()){
            case 0:
                intent.putExtra("720", a);
                Log.d(LOG_TAG, "chose" + HD[listViewHD.getCheckedItemPosition()]);
                break;
            case 1:
                intent.putExtra("900", a);
                Log.d(LOG_TAG, "chose" + HD[listViewHD.getCheckedItemPosition()]);
                break;
            case 2:
                intent.putExtra("1080", a);
                Log.d(LOG_TAG, "chose" + HD[listViewHD.getCheckedItemPosition()]);
                break;
            case 3:
                intent.putExtra("1440", a);
                Log.d(LOG_TAG, "chose" + HD[listViewHD.getCheckedItemPosition()]);
                break;

        }
            startActivity(intent);
            finish();
            Log.d(LOG_TAG, "checked: " + HD[listViewHD.getCheckedItemPosition()]);
        }
        else if(listViewHD.getCheckedItemPosition()!=-1){
            button.setEnabled(false);

        }

    }

}

