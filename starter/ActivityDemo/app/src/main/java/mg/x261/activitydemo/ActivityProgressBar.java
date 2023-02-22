package mg.x261.activitydemo;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityProgressBar extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progressbar);
        ProgressBar myProgressBar = findViewById(R.id.progressBar);
        myProgressBar.setProgress(0);

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                mProgressStatus++;
                myProgressBar.setProgress(mProgressStatus);
                mHandler.postDelayed(this, 100);
            }
        };

        mHandler.post(mRunnable);




    }


}
