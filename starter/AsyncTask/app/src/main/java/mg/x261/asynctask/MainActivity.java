package mg.x261.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);


    }

    public void btnStart(View view) {
        new DoingTaskAsync(this).execute("demoUrl");

    }

    /**
     * Simulation : doing some downloads
     */

    private class DoingTaskAsync extends AsyncTask<String, Integer, Long> {

        private Activity activity;
        ProgressDialog progressDialog;

        public DoingTaskAsync(Activity activity){
            this.activity = activity;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d("TAG", "Update : " +String.valueOf(progress[0]));
            progressDialog.setMessage("Processing ..."+progress[0] + "%");
            progressDialog.show();


        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Starting task....");
            progressDialog.show();
        }

        @Override
        protected Long doInBackground(String... strings) {
            String url = strings[0]; // Just to illustrate how to get that var value
            int count = 10;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader(); //1 seconds
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        protected void onPostExecute(Long result) {
            // TODO: Task done, udpate the UI
            display.setText("Downloaded completed");
            progressDialog.dismiss();

        }
    }

    private long Downloader() {
        // Simulation of the wait time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

}