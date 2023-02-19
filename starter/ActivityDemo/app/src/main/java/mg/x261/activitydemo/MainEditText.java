package mg.x261.activitydemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainEditText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);

        EditText editText = findViewById(R.id.editText);

        // Get the text entered in the EditText
        String text = editText.getText().toString();

        // Set the hint text for the EditText
        editText.setHint("Enter your name");

        // Set the input type for the EditText
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        // Set the maximum length for the EditText
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        // Set a listener to get notified when the user types something
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something with the text entered in the EditText
                String text = s.toString();
                Log.d("EditText", "Text entered: " + text);
            }
        });
    }
}
