package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnToast(view: View) {
        Toast.makeText(this, "This is a Toast message", Toast.LENGTH_LONG).show()
    }

    fun btnStartActivity(view: View) {
        startActivity(Intent(this, Display::class.java))
    }


}
