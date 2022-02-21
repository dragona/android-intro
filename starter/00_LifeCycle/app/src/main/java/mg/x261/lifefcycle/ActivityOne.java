package mg.x261.lifefcycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityOne extends AppCompatActivity {

    private final String TAG = "TAG_" + getClass().getSimpleName();
    private String currentState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate:" + TAG);
        currentState = "onCreate";
        setText();

        findViewById(R.id.btn_start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), ActivitySecond.class);
                startActivity(mIntent);

            }
        });
        findViewById(R.id.btn_start_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Dialog.class));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + TAG);
        currentState = "onResume";
        setText();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: " + TAG);
        currentState = "onRestart";
        setText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + TAG);
        currentState = "onPause";
        setText();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + TAG);
        currentState = "onStop";
        setText();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + TAG);
        currentState = "onDestroy";
        setText();
    }

    private void setText() {
        String previousContent = ((TextView) findViewById(R.id.display)).getText().toString();

        ((TextView) findViewById(R.id.display)).setText(previousContent + "\n" + currentState);
    }

}
