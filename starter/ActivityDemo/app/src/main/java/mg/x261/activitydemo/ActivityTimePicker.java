package mg.x261.activitydemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTimePicker extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_time_picker);
        TimePicker myTimePicker = findViewById(R.id.my_timepicker);
        int hour = myTimePicker.getHour();
        int minute = myTimePicker.getMinute();
        Log.d("TAG", "Hour: " + hour + " - Minute: " + minute);


    }
}
