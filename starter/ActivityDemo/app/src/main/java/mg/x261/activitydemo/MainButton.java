package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainButton extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        button = findViewById(R.id.button);
        /**
         * The lambda function sets an OnClickListener to the "button" instance,
         * which will execute the code within the curly braces when the button
         * is clicked. Specifically, it sets the text of "textView" to "Hello, World!".
         */
        button.setOnClickListener(v -> Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show());
    }
}
