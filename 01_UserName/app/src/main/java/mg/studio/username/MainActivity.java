package mg.studio.username;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Todo 1: get the user input if any and print all inputs in the editText display
     *
     * @param view
     */
    public void getText(View view) {
        if (((EditText) findViewById(R.id.et_name)).getText().toString().length() < 1) {
            Toast.makeText(this, "No user input!", Toast.LENGTH_LONG).show();
        } else {
            TextView mDisplay = findViewById(R.id.display);
            EditText mEditText = findViewById(R.id.et_name);
            String mTextPrevious = mDisplay.getText().toString();
            if (mTextPrevious.length() > 0) {
                mDisplay.setText(mTextPrevious.concat("\n" + mEditText.getText().toString()));
            } else {
                mDisplay.setText(mTextPrevious.concat(mEditText.getText().toString()));
            }
            mEditText.setText("");


        }
    }
}
