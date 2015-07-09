package com.freshmeat.whitesteel.randomizer_2.formats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.freshmeat.whitesteel.randomizer_2.MainActivity;
import com.freshmeat.whitesteel.randomizer_2.R;

public class Wide_Choice extends Activity implements View.OnClickListener {

    final String LOG_TAG="mLOGs";
    ListView listViewWide;
    String[] Wide;
    Button button;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wide_resolutions);

        listViewWide = (ListView) findViewById(R.id.listViewWide);
        listViewWide.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Wide,
                android.R.layout.simple_list_item_single_choice);
        listViewWide.setAdapter(adapter);

        button = (Button) findViewById(R.id.btnOK2);
        button.setOnClickListener(this);
        Wide = getResources().getStringArray(R.array.Wide);
    }

    @Override
    public void onClick(View view) {
        if(listViewWide.getCheckedItemPosition()!=-1){
            button.setEnabled(true);

            Intent intent = new Intent(this, MainActivity.class);
            switch (listViewWide.getCheckedItemPosition()){
                case 0:
                    intent.putExtra("800", a);
                    Log.d(LOG_TAG, "chose" + Wide[listViewWide.getCheckedItemPosition()]);
                    break;
                case 1:
                    intent.putExtra("1050", a);
                    Log.d(LOG_TAG, "chose" + Wide[listViewWide.getCheckedItemPosition()]);
                    break;
                case 2:
                    intent.putExtra("1200", a);
                    Log.d(LOG_TAG, "chose" + Wide[listViewWide.getCheckedItemPosition()]);
                    break;
                case 3:
                    intent.putExtra("1600", a);
                    Log.d(LOG_TAG, "chose" + Wide[listViewWide.getCheckedItemPosition()]);
                    break;

            }
            startActivity(intent);
            Log.d(LOG_TAG, "checked: " + Wide[listViewWide.getCheckedItemPosition()]);
        }
        else if(listViewWide.getCheckedItemPosition()!=-1){
            button.setEnabled(false);

        }

    }
}


