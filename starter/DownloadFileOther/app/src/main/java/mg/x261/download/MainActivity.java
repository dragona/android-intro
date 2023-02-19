package mg.x261.download;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button mDownloadButton;
    private EditText mUrlEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadButton = findViewById(R.id.download_button);
        mUrlEditText = findViewById(R.id.url_edit_text);
        mResultTextView = findViewById(R.id.result_text_view);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mUrlEditText.getText().toString();

                if (url.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (url.matches("(https?)://[a-zA-Z0-9./_-]+")) {
                    // URL is valid, proceed with the request
                    mDownloadButton.setEnabled(false); // Disable the button
                    new DownloadTask().execute(url);
                } else {
                    // URL is not valid, show an error message to the user
                    Toast.makeText(MainActivity.this, "Invalid URL. Please enter a valid HTTPS or HTTP URL.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDownloadButton.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            String url = urls[0];
            HttpURLConnection urlConnection = null;

            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("User-Agent", USER_AGENT);
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "Failed";
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mDownloadButton.setEnabled(true); // Enable the button
            mResultTextView.setText(s);
        }
    }

}
