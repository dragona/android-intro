/**
 * MainActivityCallingService is the main activity of the application that calls the TimerService.
 * It displays a start/stop button to start and stop the TimerService, and shows the current timer value in a TextView.
 */
package mg.x261.chronometer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityCallingService extends AppCompatActivity {
    private Button startStopButton;

    private TextView timerDisplay;

    boolean isTimerRunning = false;

    /**
     * Called when the activity is first created. It is used to initialize the UI components of the activity.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startStopButton = findViewById(R.id.startStopButton);
        startStopButton.setText("Start Timer");
        timerDisplay = findViewById(R.id.timerDisplay);
    }

    /**
     * Called when the start/stop button is clicked. It starts or stops the TimerService based on the current state of the button.
     * @param view The view that was clicked.
     */
    public void btnController(View view) {
        if (!isTimerRunning) {
            Intent serviceIntent = new Intent(this, TimerService.class);
            startService(serviceIntent);
            startStopButton.setText("Stop Timer");
            isTimerRunning = true;
        } else {
            stopService(new Intent(this, TimerService.class));

            startStopButton.setText("Start Timer");
            isTimerRunning = false;
        }
    }
}
