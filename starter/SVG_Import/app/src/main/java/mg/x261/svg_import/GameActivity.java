package mg.x261.svg_import;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
// Variables to store game related data
String[] listAnswer;
CustomDialog alert;
List<Integer> flagsToLearnImageResources;
ArrayAdapter<String> mAdapter;
private int numberOfFlagsToRecognize;
private TextView textView;
private int trackerCurrentCorrectAnswer = 0;
private ListView myListView;
private TextView tvTracker;
private ImageView imageFlag;
private String text_answer;
private final int numberFlagsToLearn = 10;
private DbHelper databaseHelper;
private int mistakeCounts = 0;
private boolean firstMistakeFlag = true;

// Method to create the activity and set the layout
@RequiresApi(api = Build.VERSION_CODES.N)
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    // Retrieve the continent selected from the previous activity
    World world = new World();
    databaseHelper = new DbHelper(this);
    Bundle extras = getIntent().getExtras();
    int continentSelectorIndex = extras.getInt("continent_selected");

    // Check if the selected index is valid and go back to selection if not
    if ((continentSelectorIndex != (int) continentSelectorIndex) || (continentSelectorIndex >= world.getContinentsSize())) {
        startActivity(new Intent(this, ContinentActivity.class));
        finish();
    }

    // Set the title of the activity and retrieve the countries in the selected continent
    String continenSelected = world.getContinents()[continentSelectorIndex];
    setTitle(getString(R.string.app_name) + " : " + continenSelected);
    String[] continents = world.getCountries(continenSelected);
    int max_flags_loaded = continents.length;
    List<String> _data = Arrays.asList(continents);
    List<String> flagsToLearn = _data.subList(0, numberFlagsToLearn);
    Collections.shuffle(_data);
    
    // Get the image resources for the flags to learn
    flagsToLearnImageResources = new ArrayList<>();
    for (int i = 0; i < flagsToLearn.size(); i++) {
        String imageId = flagsToLearn.get(i);
        int flagResIDs = getResources().getIdentifier(imageId, "drawable", getPackageName());
        flagsToLearnImageResources.add(flagResIDs);
    }

        /**
         *  Game logic
         *  [] generate the first flag with the possible answers
         *  [] generate a dialog if the answer is wrong, stay on the same question
         *  [] TODO: the flags that were not known in the previous game session should be shown
         *      again when
         *  [] TODO: when a flag that is already in the history (DB) is shown again to the user
         *      as a question and he knows the correct answer, the pair country flag should be
         *      removed from the DB
         *
         */

        // The game starts
        numberOfFlagsToRecognize = numberFlagsToLearn;
        tvTracker = (TextView) findViewById(R.id.tvStatusGame);
        imageFlag = (ImageView) findViewById(R.id.imageViewFlag);
        myListView = findViewById(R.id.listFlagGameQuestion);
        String flag_resource_name = flagsToLearn.get(trackerCurrentCorrectAnswer);
        text_answer = world.toCountryName(flag_resource_name);
        // TODO: random generation of alternative answers
        listAnswer = new String[]{text_answer, "Demo start", "Demo 1", "Demo 2"};
        updateQuestion();


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                if (!listAnswer[itemPosition].equals(text_answer)) {

                    if (firstMistakeFlag) {
                        mistakeCounts += 1;
                        firstMistakeFlag = false;
                        // Record my mistakes
                        Log.d("TAG", "continentSelectorIndex - flag_resource_name:" + continentSelectorIndex + " - " + flag_resource_name);
                        recordMistake(continentSelectorIndex, flag_resource_name); // persistent data tracker
                    }
                    ((RadioButton) view.findViewById(R.id.radioCountrySelector)).setChecked(true);
                    changeColorRadio((RadioButton) view.findViewById(R.id.radioCountrySelector));
                    alert = new CustomDialog(GameActivity.this);
                    alert.showDialog();

                } else {
                    // if not the last flag to guess, go to next else move to the report
                    numberOfFlagsToRecognize -= 1;
                    if (numberOfFlagsToRecognize == 0) {
                        // Go to the report activity
                        Intent intent = new Intent(getBaseContext(), GameReport.class);
                        Log.d("TAG", "mistakeCounts : " + mistakeCounts + " - numberFlagsToLearn: " + numberFlagsToLearn);
                        intent.putExtra("mistake_counts", mistakeCounts);
                        intent.putExtra("total_questions", numberFlagsToLearn);
                        startActivity(intent);
                        finish();
                    } else {
                        firstMistakeFlag = true; // Did not make a mistake yet (new question)
                        trackerCurrentCorrectAnswer += 1;
                        text_answer = world.toCountryName(flagsToLearn.get(trackerCurrentCorrectAnswer));
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
        tvTracker.setText(MessageFormat
                .format("{0}/{1}",
                        (numberFlagsToLearn - numberOfFlagsToRecognize) + 1,
                        numberFlagsToLearn));
        imageFlag.setImageResource(flagsToLearnImageResources.get(trackerCurrentCorrectAnswer));
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


    // Tack my mistakes
    public void recordMistake(int continentId, String flag_resource_name) {
        // TODO: should only record when it is not in the database
        boolean dataInserted = databaseHelper.insertData(String.valueOf(continentId), flag_resource_name);
        if (dataInserted) { // is True
            //Do something
            Log.d(getPackageName(), "Data inserted");

        } else {
            //Show an error
            Log.e(getPackageName(), " Failed inserted data");
        }
    }

}
