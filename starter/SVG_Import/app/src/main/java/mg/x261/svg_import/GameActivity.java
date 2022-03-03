package mg.x261.svg_import;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    String[] listAnswer;
    CustomDialog alert;
    List<Integer> flagsToLearnImageResources;
    ArrayAdapter<String> mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // Mockup continent selected
        String[] continentSouthAmericaNames = {
                "flag_argentina",
                "flag_bolivia",
                "flag_brazil",
                "flag_chile",
                "flag_colombia",
                "flag_guyana",
                "flag_paraguay",
                "flag_peru",
                "flag_suriname",
                "flag_uruguay",
                "flag_venezuela",
        };
        int max_flags_loaded = continentSouthAmericaNames.length;
        int number_flags_to_learn = 10;
        List<String> _data = Arrays.asList(continentSouthAmericaNames);
        Collections.shuffle(_data);
        List<String> flagsToLearn = _data.subList(0, number_flags_to_learn);
        // Getting the flags image resources ready
        flagsToLearnImageResources = new ArrayList<>();
        for (int i = 0; i < flagsToLearn.size(); i++) {
            String imageId = flagsToLearn.get(i);
            int flagResIDs = getResources().getIdentifier(imageId, "drawable", getPackageName());
            flagsToLearnImageResources.add(flagResIDs);
        }

        /**
         * Game logic
         *  generate the first flag with the possible answers
         *  generate a dialog if the answer is wrong, stay on the same question
         *  TODO: When the correct answer is selected, move to the next question.
         *
         */

        int tracker_current_correct_answer = 0;
        ((ImageView) findViewById(R.id.imageViewFlag)).setImageResource(flagsToLearnImageResources.get(tracker_current_correct_answer));
        // Load the possible answers
        String text_answer = flagsToLearn.get(tracker_current_correct_answer).replace("flag_", "");
        listAnswer = new String[]{text_answer, "Asia", "Australia", "Europe"};
        mAdapter = new ArrayAdapter<String>(
                this,
                R.layout.item_game_question,
                R.id.tvCountry,
                listAnswer
        );

        ListView myListView = findViewById(R.id.listFlagGameQuestion);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                //TODO: Open the question
                Toast.makeText(getApplicationContext(), listAnswer[itemPosition], Toast.LENGTH_SHORT).show();
                // not correct answer selected, set the radio checked to red and open a dialog
                if (!listAnswer[itemPosition].equals(text_answer)) {
                    ((RadioButton) view.findViewById(R.id.radioCountrySelector)).setChecked(true);
                    changeColorRadio((RadioButton) view.findViewById(R.id.radioCountrySelector));
                    alert = new CustomDialog(GameActivity.this);
                    alert.showDialog();

                } else {
                    //TODO: Continue the game
                    startActivity(new Intent(GameActivity.this, GameReport.class));
                    finish();

                }
            }
        });


    }


    // Closing the dialog
    public void closeDialog(View view) {
        alert.closeDialog();
    }

    // Changing the TintColor of a radio button
    public void changeColorRadio(RadioButton radio) {
        ColorStateList colorStateList = new ColorStateList(
                new int[][]
                        {
                                new int[]{-android.R.attr.state_enabled}, // Disabled
                                new int[]{android.R.attr.state_enabled}   // Enabled
                        },
                new int[]
                        {
                                Color.BLACK, // disabled
                                Color.RED
                        }
        );
        radio.setButtonTintList(colorStateList); // set the color tint list
        radio.invalidate();
    }


}