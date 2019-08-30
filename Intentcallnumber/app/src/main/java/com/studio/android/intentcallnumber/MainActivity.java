package com.studio.android.intentcallnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_call(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:520510")));

    }
}
