package mg.x261.mymultiautocompletetextview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityDemo2 extends AppCompatActivity {

    private List<String> statesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] statesArray = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
                "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
                "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
                "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

        statesList = new ArrayList<>(Arrays.asList(statesArray));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statesList);

        MultiAutoCompleteTextView stateText = findViewById(R.id.state_text);
        stateText.setAdapter(adapter);
        stateText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        stateText.setOnItemClickListener((parent, view, position, id) -> {
            String selectedState = (String) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), "Selected state: " + selectedState, Toast.LENGTH_SHORT).show();

            // remove the selected state from the list and update the adapter
            statesList.remove(selectedState);
            adapter = new ArrayAdapter<>(MainActivityDemo2.this, android.R.layout.simple_dropdown_item_1line, statesList);
            stateText.setAdapter(adapter);
        });

    }
}
