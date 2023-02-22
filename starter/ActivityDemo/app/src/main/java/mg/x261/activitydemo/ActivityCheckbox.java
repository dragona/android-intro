package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityCheckbox extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_checkbox);
        CheckBox myCheckBox = findViewById(R.id.my_checkbox);
        myCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // This method will be called when the state of the checkbox changes
                if (isChecked) {
                    // Do something when the checkbox is checked
                } else {
                    // Do something when the checkbox is unchecked
                }
            }
        });

    }
}
