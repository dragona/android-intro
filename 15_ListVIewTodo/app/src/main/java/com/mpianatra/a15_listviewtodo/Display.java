package com.mpianatra.a15_listviewtodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Display extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String data = getIntent().getExtras().getString("name_selected", "None");
        ((TextView) findViewById(R.id.tv_display)).setText(data);
    }
}
