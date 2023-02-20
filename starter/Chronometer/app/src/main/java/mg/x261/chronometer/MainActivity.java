package mg.x261.chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private Chronometer timerDisplay;
    private Button startStopButton;
    Boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerDisplay = findViewById(R.id.timerDisplay);
        startStopButton = findViewById(R.id.startStopButton);
        startStopButton.setText("Start timer");
    }

    public void btnController(View view) {
        if (!isTimerRunning) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    private void startTimer() {
        timerDisplay.setBase(SystemClock.elapsedRealtime());
        timerDisplay.start();
        isTimerRunning = true;
        startStopButton.setText("Stop timer");
    }

    private void stopTimer() {
        timerDisplay.stop();
        isTimerRunning = false;
        startStopButton.setText("Start timer");
    }
}
