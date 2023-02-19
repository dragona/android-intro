package com.example.myapplication


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextDescription = findViewById<EditText>(R.id.edit_text_description)
        val buttonSubmit: Button = findViewById(R.id.button_submit)

        buttonSubmit.setOnClickListener { // handle the event here
            // for example, get the text from the EditText and do something with it
            val text = editTextDescription.text.toString()
        }

        editTextDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // do nothing
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // do something with the text as it is being changed
            }
            override fun afterTextChanged(s: Editable) {
                // do something with the text after it has changed
            }
        })


    }
}