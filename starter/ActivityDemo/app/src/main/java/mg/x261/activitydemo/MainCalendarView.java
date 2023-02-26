package mg.x261.activitydemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainCalendarView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar);

        // Find the CalendarView widget with the ID "calendarView" in the layout
        CalendarView myCalendarView = findViewById(R.id.calendarView);

        // Set an OnDateChangeListener for the CalendarView, which will be called when the user selects a date
        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Handle changes to the selected date here
                // The "year", "month", and "dayOfMonth" parameters contain the selected date values
                Log.d("TAG", "Yeah "+ year + "- Month " + month + " - Day "+ dayOfMonth);
            }
        });


    }
}
