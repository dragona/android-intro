package mg.x261.activitydemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDatePicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);
        DatePicker myDatePicker = findViewById(R.id.my_datepicker);
        int year = myDatePicker.getYear();
        int month = myDatePicker.getMonth();
        int day = myDatePicker.getDayOfMonth();
        Log.d("TAG", "Year: " + year + " - Month: " + month + " - Day: " + day);

    }
}
