package mg.x261.chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private Chronometer timer;
    Boolean flag = false;
    private Button button;
    final String START = "Start Timer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.display);
        button = findViewById(R.id.button);
        button.setText(START);
    }

    public void btnController(View view) {
        if (!flag) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    private void startTimer() {
        //Start timer from 0
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        flag = true;
        button.setText("Stop Timer");
    }

    private void stopTimer() {
        //Stop Timer
        timer.stop();
        flag = false;
        button.setText(START);
    }
}