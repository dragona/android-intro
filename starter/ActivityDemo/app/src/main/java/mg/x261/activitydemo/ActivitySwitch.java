package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.CompoundButton;

import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySwitch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_switch);

        Switch mySwitch = findViewById(R.id.switch1);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // Handle changes to the Switch's state here
                // The "isChecked" is then set to either true if on, or false if off)
                Toast.makeText(
                        getBaseContext(),
                        "State " + mySwitch.isChecked(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
