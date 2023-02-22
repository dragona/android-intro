package mg.x261.activitydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRunnableThreadHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_runnable_thread_handler);
        /**
         * we define a new Runnable that displays a Log message when executed.
         * The run() method of the Runnable contains the code that shows the Log message.
         */
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "This is a task");
            }
        };

        /**
         * To execute the Runnable, we can pass it to a Thread object
         * and call the start() method of the Thread
         */
        Thread myThread = new Thread(myRunnable);
        myThread.start();

        // Create a Handler that will run on the main thread
        Handler handler = new Handler(Looper.getMainLooper());

        // Define a Runnable that shows the Toast message
        Runnable myRunnableTwo = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        "Hello from a Runnable!",
                        Toast.LENGTH_SHORT).show();
            }
        };

        // Post the Runnable to the main thread's message queue
        handler.post(myRunnableTwo);


    }


}
