package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class MainTextView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        // Get current date and format it
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        String formattedDate = dateFormat.format(currentDate);

        // Set the formatted date as the text of the TextView
        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(formattedDate);
    }
}
