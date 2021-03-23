package com.mpianatra.a21_webview_todo;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://studio.mg");
    }
}