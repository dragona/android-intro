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

            // Override the onItemClick method to launch the corresponding activity based on the clicked item position.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    // Get the class object of the activity to launch using its name from the activityNames array.
                    Class<?> activityClass = Class.forName(getPackageName() + "." + activityNames[position]);
                    // Create an Intent to launch the activity and start it.
                    Intent intent = new Intent(MainActivity.this, activityClass);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    // Print the stack trace if the activity class cannot be found.
                    e.printStackTrace();
                }
            }
        });

    }
}

