package mg.x261.myvolleyprogresstrack;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private static final int REQUEST_MANAGE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        // Check if we have manage external storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                // Permission already granted, start download
                startDownload();
            } else {
                // Request permission if it hasn't been granted
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_MANAGE_EXTERNAL_STORAGE);
            }
        } else {
            // For devices running Android 10 and below, no permission needed, start download
            startDownload();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MANAGE_EXTERNAL_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // Permission granted, start download
                    startDownload();
                } else {
                    // Permission denied, show error message
                    showToast("Permission denied");
                }
            }
        }
    }

    private void startDownload() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://studio.mg/files/demo-api.mp4";
        DownloadRequest request = new DownloadRequest(url, new DownloadListener() {
            @Override
            public void onProgressUpdate(int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onComplete(File file) {
                showToast("Download complete");
            }

            @Override
            public void onError() {
                showToast("Download error");
            }
        }, MainActivity.this);

        queue.add(request);
    }

    private void showToast(String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        });
    }

    public interface DownloadListener {
        void onProgressUpdate(int progress);

        void onComplete(File file);

        void onError();
    }

    public class DownloadRequest extends Request<File> {

        private static final int BUFFER_SIZE = 1024 * 4;
        private final String mUrl;
        private final DownloadListener mListener;
        private final Context mContext;

        public DownloadRequest(String url, DownloadListener listener, Context context) {
            super(Method.GET, url, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            });
            mUrl = url;
            mListener = listener;
            mContext = context;
        }

        @Override
        protected Response<File> parseNetworkResponse(NetworkResponse response) {
            File file = null;
            try {
                // Create a new file in the app's private storage directory
                String fileName = "downloaded_file.mp4";


                File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyApp");
                Log.d("DownloadRequest", "downloadsDir: " + downloadsDir.getAbsolutePath());
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdir();
                }
                file = new File(downloadsDir, fileName);
                Log.d("DownloadRequest", "file: " + file.getAbsolutePath());




                FileOutputStream fos = new FileOutputStream(file);

                byte[] data = response.data;
                ByteArrayInputStream bis = new ByteArrayInputStream(data);

                long totalBytes = -1;
                String contentLength = response.headers.get("content-length");
                if (contentLength != null) {
                    totalBytes = Long.parseLong(contentLength);
                }

                byte[] buffer = new byte[BUFFER_SIZE];
                int len;
                long downloadedBytes = 0;
                while ((len = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    downloadedBytes += len;
                    // Update progress
                    int progress = (int) (downloadedBytes * 100 / totalBytes);
                    Log.d("DownloadRequest", "Downloaded " + progress + "%");
                    mListener.onProgressUpdate(progress);
                }

                fos.flush();
                fos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
                mListener.onError();
            }
            return Response.success(file, HttpHeaderParser.parseCacheHeaders(response));
        }

        @Override
        protected void deliverResponse(File response) {
            mListener.onComplete(response);
        }

        @Override
        public void deliverError(VolleyError error) {
            mListener.onError();
        }
    }



}
