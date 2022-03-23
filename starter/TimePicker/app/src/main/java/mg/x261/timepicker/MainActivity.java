package mg.x261.timepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int hour, minute;
    TimePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picker = findViewById(R.id.myTimePicker);
        picker.setIs24HourView(true);

        getTime(picker);


    }


    private void getTime(TimePicker picker) {
        // Get the hour and time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = picker.getHour();
            minute = picker.getMinute();
        } else {
            hour = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        }

        Toast.makeText(this,
                " Time is  " + hour + ":" + minute, Toast.LENGTH_LONG).show();
    }

    public void btnGetTime(View view) {
        getTime(picker);
    }
}