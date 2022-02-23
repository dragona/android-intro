package mg.x261.username;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getText(View view) {
        TextView mDisplay = findViewById(R.id.display);
        EditText mEditText = findViewById(R.id.et_name);
        mDisplay.setText(mEditText.getText().toString());
    }
}