package mg.x261.onboardingflow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewFlipper mViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewFlipper = findViewById(R.id.view_flipper);

        for (int i = 0; i < mViewFlipper.getChildCount(); i++) {
            mViewFlipper.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        mViewFlipper.showNext();
    }
}