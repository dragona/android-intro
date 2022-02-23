package mg.x261.editttext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Connect the buttons and the edit text with this java file
        Button btnClear = (Button) findViewById(R.id.btnClear);
        Button btnToast = (Button) findViewById(R.id.btnGet);
        final EditText myEdittext = (EditText) findViewById(R.id.et_demo);

        // Set the on click listeners

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the content of the EditText
                myEdittext.setText(null);

            }
        });

        // Set the on click listeners using lambda
        btnToast.setOnClickListener(v -> {
            // Retrieve the string from the edit text
            String userInput = myEdittext.getText().toString();
            // display the string as a toast
            Toast.makeText(getBaseContext(), userInput, Toast.LENGTH_LONG).show();

        });
    }
}