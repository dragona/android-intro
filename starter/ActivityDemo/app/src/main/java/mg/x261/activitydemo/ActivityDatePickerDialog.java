package mg.x261.activitydemo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ActivityDatePickerDialog extends AppCompatActivity {

    EditText dateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_datepicker_form);
        dateField = findViewById(R.id.date_field);
        Button dateButton = findViewById(R.id.date_button);

        // Set a click listener on the button to display the DatePicker dialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });


        // Get a reference to the date field EditText view
        EditText dateField = findViewById(R.id.date_field);

        // Add a TextWatcher to format the input text as the user types


// Add a TextWatcher to format the input text as the user types
        dateField.addTextChangedListener(new TextWatcher() {
            private static final int MAX_LENGTH = 10;
            private String current = "";
            private String ddmmyyyy = "ddmmyyyy";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    // Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        // This part makes sure that the date is valid
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        // with leap years - otherwise, date e.g. 29/02/2012
                        // would be treated as 29/02/2013
                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateField.setText(current);
                    dateField.setSelection(sel < MAX_LENGTH ? sel : MAX_LENGTH);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        });



    }


    private void showDatePickerDialog() {
        // Get the current date from the EditText field
        String dateString = dateField.getText().toString();

        // If the date is not empty, parse the year, month, and day values from the string
        int year = -1;
        int month = -1;
        int day = -1;
        if (!dateString.isEmpty()) {
            try {
                String[] dateParts = dateString.split("/");
                year = Integer.parseInt(dateParts[2]);
                month = Integer.parseInt(dateParts[1]) - 1; // Month is zero-based
                day = Integer.parseInt(dateParts[0]);
            } catch (NumberFormatException e) {
                // Handle invalid date format
                dateField.setText("");
            }
        }

        // If the date is empty or invalid, use the current date
        if (year == -1 || month == -1 || day == -1) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }


        // Create a new DatePickerDialog and set the initial date to the current date
        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityDatePickerDialog.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Format the selected date as a string in the format "dd/mm/yyyy"
                String formattedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                // Update the EditText field with the formatted date
                dateField.setText(formattedDate);
            }

        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

}
