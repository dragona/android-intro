package mg.x261.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView activityListView;

    private String[] activityNames = {"ActivityCheckbox", "ActivityDatePicker", "ActivityDatePickerDialog",
            "ActivityListView", "ActivityProgressBar", "ActivityRadioButton", "ActivityRating",
            "ActivityRunnableThreadHandler", "ActivityScrollView", "ActivitySpinner", "ActivitySwitch",
            "ActivityTimePicker", "LoadImage", "MainButton", "MainCalendarView", "MainDialog",
            "MainEditText", "MainImageView", "MainLinearLayout", "MainRelativeLayout", "MainSeekBar",
            "MainTable", "MainTextView", "MainToast"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ListView with the id activity_list_view in the activity layout and assign it to activityListView variable.
        activityListView = findViewById(R.id.activity_list_view);

        // Create an ArrayAdapter with a simple_list_item_1 layout and activityNames array as data source.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityNames);

        // Set the adapter to the activityListView to display the list of activity names.
        activityListView.setAdapter(adapter);

        // Set an OnItemClickListener to the activityListView to handle clicks on the list items.
        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (activityNames[position]) {
                    case "ActivityCheckbox":
                        startActivity(new Intent(MainActivity.this, ActivityCheckbox.class));
                        break;
                    case "ActivityDatePicker":
                        startActivity(new Intent(MainActivity.this, ActivityDatePicker.class));
                        break;
                    case "ActivityDatePickerDialog":
                        startActivity(new Intent(MainActivity.this, ActivityDatePickerDialog.class));
                        break;
                    case "ActivityListView":
                        startActivity(new Intent(MainActivity.this, ActivityListView.class));
                        break;
                    case "ActivityProgressBar":
                        startActivity(new Intent(MainActivity.this, ActivityProgressBar.class));
                        break;
                    case "ActivityRadioButton":
                        startActivity(new Intent(MainActivity.this, ActivityRadioButton.class));
                        break;
                    case "ActivityRating":
                        startActivity(new Intent(MainActivity.this, ActivityRating.class));
                        break;
                    case "ActivityRunnableThreadHandler":
                        startActivity(new Intent(MainActivity.this, ActivityRunnableThreadHandler.class));
                        break;
                    case "ActivityScrollView":
                        startActivity(new Intent(MainActivity.this, ActivityScrollView.class));
                        break;
                    case "ActivitySpinner":
                        startActivity(new Intent(MainActivity.this, ActivitySpinner.class));
                        break;
                    case "ActivitySwitch":
                        startActivity(new Intent(MainActivity.this, ActivitySwitch.class));
                        break;
                    case "ActivityTimePicker":
                        startActivity(new Intent(MainActivity.this, ActivityTimePicker.class));
                        break;
                    case "LoadImage":
                        startActivity(new Intent(MainActivity.this, LoadImage.class));
                        break;
                    case "MainButton":
                        startActivity(new Intent(MainActivity.this, MainButton.class));
                        break;
                    case "MainCalendarView":
                        startActivity(new Intent(MainActivity.this, MainCalendarView.class));
                        break;
                    case "MainDialog":
                        startActivity(new Intent(MainActivity.this, MainDialog.class));
                        break;
                    case "MainEditText":
                        startActivity(new Intent(MainActivity.this, MainEditText.class));
                        break;
                    case "MainImageView":
                        startActivity(new Intent(MainActivity.this, MainImageView.class));
                        break;
                    case "MainLinearLayout":
                        startActivity(new Intent(MainActivity.this, MainLinearLayout.class));
                        break;
                    case "MainRelativeLayout":
                        startActivity(new Intent(MainActivity.this, MainRelativeLayout.class));
                        break;
                    case "MainSeekBar":
                        startActivity(new Intent(MainActivity.this, MainSeekBar.class));
                        break;
                    case "MainTable":
                        startActivity(new Intent(MainActivity.this, MainTable.class));
                        break;
                    case "MainTextView":
                        startActivity(new Intent(MainActivity.this, MainTextView.class));
                        break;
                    case "MainToast":
                        startActivity(new Intent(MainActivity.this, MainToast.class));
                        break;
                    default:
                        break;
                }
            }
        });


    }
}

