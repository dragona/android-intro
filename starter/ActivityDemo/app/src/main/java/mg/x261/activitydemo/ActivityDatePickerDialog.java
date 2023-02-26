package mg.x261.activitydemo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ActivityDatePickerDialog extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_datepicker_form);
        EditText dateField = findViewById(R.id.date_field);
        Button dateButton = findViewById(R.id.date_button);

// Set a click listener on the button to display the DatePicker dialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date from the EditText field
                String dateString = dateField.getText().toString();
                int year, month, day;

                // If the date is not empty, parse the year, month, and day values from the string
                if (!dateString.isEmpty()) {
                    String[] dateParts = dateString.split("/");
                    year = Integer.parseInt(dateParts[2]);
                    month = Integer.parseInt(dateParts[1]) - 1; // Month is zero-based
                    day = Integer.parseInt(dateParts[0]);
                } else {
                    // If the date is empty, use the current date
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                }

                // Create a new DatePickerDialog and set the initial date to the current date

                // In Android, this and getApplicationContext() are both used to obtain a Context object,
                // which provides access to system resources and allows you to interact with the user interface.
                // However, they refer to different types of Context objects:

                // - this refers to the Context of the current object. When used in an Activity class,
                // for example, this refers to the Context of that Activity.

                // - getApplicationContext() is a method of the Context class that returns the Context of the
                // entire application. This Context is not tied to any specific activity or component, and can
                // be used throughout the application.

                // - YourActivityName.this is similar to this, but it explicitly refers to the Context of a
                // specific activity, rather than the current object. It is often used when passing a Context
                // object to methods that require an Activity context, such as when creating an Intent or a Dialog.

                // To summarize:

                // - Use this when you need to obtain the Context of the current object, such as an Activity.
                // - Use getApplicationContext() when you need to obtain the Context of the entire application.
                // - Use YourActivityName.this when you need to obtain the Context of a specific activity.
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityDatePickerDialog.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText field with the selected date
                        dateField.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

    }
}
