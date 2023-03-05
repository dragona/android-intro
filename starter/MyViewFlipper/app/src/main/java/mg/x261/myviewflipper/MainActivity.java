package mg.x261.myviewflipper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the ViewFlipper
        ViewFlipper viewFlipper = findViewById(R.id.view_flipper);

        // Set the auto start and flip interval properties for the ViewFlipper
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(2000);

        // Start flipping through the Views in the ViewFlipper
        viewFlipper.startFlipping();
    }
}