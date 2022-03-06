package mg.x261.svg_import;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
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
    private int numberOfFlagsToRecognize;
    private TextView textView;
    private int tracker_current_correct_answer = 0;
    private ListView myListView;
    private TextView tvTracker;
    private ImageView imageFlag;
    private String text_answer;
    private int number_flags_to_learn = 10;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Retrieve the continent selected else go back to selection
        World world = new World();


        Bundle extras = getIntent().getExtras();
        int continentSelectorIndex = extras.getInt("continent_selected");
        if ((continentSelectorIndex != (int) continentSelectorIndex) || (continentSelectorIndex >= world.getContinentsSize())) {
            startActivity(new Intent(this, ContinentActivity.class));
            finish();
        }

        String[] continents = world.getCountries(world.getContinents()[continentSelectorIndex]);
        int max_flags_loaded = continents.length;
        List<String> _data = Arrays.asList(continents);
        List<String> flagsToLearn = _data.subList(0, number_flags_to_learn);
        Collections.shuffle(_data);
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
         */

        // The game starts
        numberOfFlagsToRecognize = number_flags_to_learn;
        tvTracker = (TextView) findViewById(R.id.tvStatusGame);
        imageFlag = (ImageView) findViewById(R.id.imageViewFlag);
        myListView = findViewById(R.id.listFlagGameQuestion);
        text_answer = world.toCountryName(flagsToLearn.get(tracker_current_correct_answer));
        // TODO: random generation of alternative answers
        listAnswer = new String[]{text_answer, "Demo start", "Demo 1", "Demo 2"};
        updateQuestion();


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                if (!listAnswer[itemPosition].equals(text_answer)) {
                    ((RadioButton) view.findViewById(R.id.radioCountrySelector)).setChecked(true);
                    changeColorRadio((RadioButton) view.findViewById(R.id.radioCountrySelector));
                    alert = new CustomDialog(GameActivity.this);
                    alert.showDialog();

                } else {
                    // if not the last flag to guess, go to next
                    numberOfFlagsToRecognize -= 1;
                    if (numberOfFlagsToRecognize == 0) {
                        // Go to the report
                        startActivity(new Intent(getBaseContext(), GameReport.class));
                        finish();
                    } else {
                        tracker_current_correct_answer += 1;
                        text_answer = world.toCountryName(flagsToLearn.get(tracker_current_correct_answer));
                        // TODO: random generation of alternative answers
                        listAnswer = new String[]{text_answer, "Demo2", "Dem 3", "Demo 3"};
                        updateQuestion();
                    }
                }
            }
        });
    }

    private void updateQuestion() {
        // Load a new question
        tvTracker.setText((number_flags_to_learn - numberOfFlagsToRecognize) + 1 + "/" + number_flags_to_learn);
        imageFlag.setImageResource(flagsToLearnImageResources.get(tracker_current_correct_answer));
        mAdapter = new ArrayAdapter<String>(
                this,
                R.layout.item_game_question,
                R.id.tvCountry,
                listAnswer
        );
        myListView.setAdapter(mAdapter);

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