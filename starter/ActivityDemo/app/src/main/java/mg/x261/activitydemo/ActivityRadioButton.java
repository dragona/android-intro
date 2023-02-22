package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRadioButton extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_radio);

        RadioGroup radioGroup = findViewById(R.id.my_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This method will be called when the selected RadioButton changes
                if (checkedId == R.id.radio_button1) {
                    // Do something when Radio Button 1 is selected
                    Toast.makeText(getApplicationContext(), "Radio 1", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radio_button2) {
                    // Do something when Radio Button 2 is selected
                    Toast.makeText(getApplicationContext(), "Radio 2", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
