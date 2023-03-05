package mg.x261.a21_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the nav bar and status bar for a full-screen experience
        hideSystemUI();

        // Show a ProgressDialog while the WebView is loading a page
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progressDialog.setCancelable(false);

        // Initialize the WebView
        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // Set a WebViewClient to override the URL loading behavior and show a progress dialog
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Show the progress dialog while the URL is loading
                progressDialog.show();
                view.loadUrl(request.getUrl().toString());

                return true;
            }

            /**
             * Callback for when a page has finished loading in the WebView.
             *
             * @param view the WebView that has finished loading the page.
             * @param url  the URL of the page that was loaded.
             */
            @Override
            public void onPageFinished(WebView view, final String url) {
                // Dismiss the progress dialog
                progressDialog.dismiss();
            }
        });

        // Enable DOM storage for the WebView
        webView.getSettings().setDomStorageEnabled(true);

        // Load the URL in the WebView
        webView.loadUrl("https://studio.mg");
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // Hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }


}
