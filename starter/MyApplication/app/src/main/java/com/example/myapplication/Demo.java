package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Demo extends AppCompatActivity {

    // Declare and initialize the EditText and Button widgets in the corresponding activity file,
    // make them accessible outside of the onCreate.
    EditText editTextDescription;
    Button buttonSubmit;
    ListView listViewTasks;
    ArrayList<String> tasksList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDescription = findViewById(R.id.edit_text_description);
        buttonSubmit = findViewById(R.id.button_submit);
        // Add a listView to our XML layout which will hold the list of task
        listViewTasks = (ListView) findViewById(R.id.list_view_tasks);

        /***
         *  Initializing the list of tasks and set up the adapter for the listView.
         * First, a new ArrayList called tasksList is created. This list will hold the string
         * values of each task that the user inputs.
         *
         * Next, an ArrayAdapter is created and initialized. The ArrayAdapter is a class from the
         * android.widget package that allows for easy binding of an ArrayList to a ListView.
         * The ArrayAdapter is passed three arguments in its constructor:
         *
         *      this: a reference to the current activity, in this case the Demo class which
         *              extends AppCompatActivity
         *      android.R.layout.simple_list_item_1 : this is a predefined layout provided by
         *              android that will be used to display the list items.
         *      tasksList : the ArrayList that will be used as the data source for the ListView.
         *
         * Finally, the adapter is set to the listView using the setAdapter method.
         * This tells the ListView to use the adapter as its data source, so that it can display
         * the items in the tasksList.
         */
        tasksList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        listViewTasks.setAdapter(adapter);

        // Set an OnClickListener for the button, which will be triggered when the user clicks on
        // the button. Inside the OnClickListener, retrieve the user input from the EditText using
        // the getText() method.
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextDescription.getText().toString();
                tasksList.add(task);
                // Each time the Button is pressed : Add the user input to the list of tasks, for
                // example using an ArrayAdapter and then notify the adapter that the data has
                // changed using notifyDataSetChanged().
                adapter.notifyDataSetChanged();
            }
        });


        // do something with the text as it is being changed
        editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do something with the text as it is being changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do something with the text after it has changed
            }
        });


    }
}
