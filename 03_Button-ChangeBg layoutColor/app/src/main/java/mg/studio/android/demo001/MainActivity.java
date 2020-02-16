package mg.studio.android.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * We will update this project so that:
 * - when a button is pressed, the background color of
 * the layout will change into RED, YELLOW, etc.
 * <p>
 * - Thus, we will first add 3 buttons
 * - When a button is pressed, the color of the bg will change
 */
public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.main_layout);
    }

    public void btnYellow(View view) {
        relativeLayout.setBackgroundColor(Color.YELLOW);

    }

    public void btnGreen(View view) {
        relativeLayout.setBackgroundColor(Color.GREEN);
    }

    public void btnRed(View view) {
        relativeLayout.setBackgroundColor(Color.RED);
    }
}
