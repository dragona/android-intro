package com.mpianatra.a19_database_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 *  Group name:
 *
 *  Member 1.
 *  Student ID:
 *  Name (Chinese):
 *  Name (English):
 *
 * Member 2.
 * Student ID:
 * Name (Chinese):
 * Name (English):
 */

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
    }


    public void btnAdd(View view) {

        // Connect the views
        EditText etName = findViewById(R.id.first_name);
        EditText etLastName = findViewById(R.id.last_name);
        EditText etMarks = findViewById(R.id.marks);

        // Get the user inputs
        String firstName = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String marks = etMarks.getText().toString();

        /** Todo 0: Do not allow data insertion when any of the edit texts
         * (first_name, last_name, marks) has an empty string.
         * Inform the user accordingly and do not clear the already filled edit text.
         */

        boolean dataInserted = databaseHelper.insertData(firstName, lastName, marks);
        if (dataInserted) { // is True
            //Do something
            Log.d(getPackageName(), "Data inserted");
            // Todo 1: Inform the user about this event by showing something meaningful
            // A Toast should be enough
            // https://developer.android.com/guide/topics/ui/notifiers/toasts

        } else {
            // Todo 2: Inform the user about this event by showing something meaningful
            // You should not use a Toast here. A toast will only show for a few seconds
            // which might not be enough for the user to read the message and understand
            // what should be done.
            // You can consider Using a dialog
            // https://developer.android.com/guide/topics/ui/dialogs

            Log.e(getPackageName(), " Failed inserted data");
        }

        // Clear the edit texts
        etName.setText("");
        etLastName.setText("");
        etMarks.setText("");

        // TODO 3:  Show the newly inserted content in a textView by using the activity Display and the view tv_display
    }




    /**
     * Read all the BD content
     * @param view
     */
    public void btnReadDb(View view) {
        /** TODO 4: Update this part so that :
         *  1- When the button is pressed, its color changes to purple_200
         *  2- When the button is pressed, the activity DisplayAll should start and it should
         *  show the content of the database in a listView
         *
         */
        Cursor cursor = databaseHelper.readData();

        if (cursor.getCount() == 0) {
            //The table is empty
            Log.e(getPackageName(), " Reader: empty");
            ((TextView) findViewById(R.id.tv_display)).setText("The database is empty");
        } else {

            Log.e(getPackageName(), " Reading the content");
            StringBuilder stringBuffer = new StringBuilder();
            while (cursor.moveToNext()) {
                stringBuffer.append("Id: ").append(cursor.getString(0)).append("\n");
                stringBuffer.append("First name: ").append(cursor.getString(1)).append("\n");
                stringBuffer.append("Last name: ").append(cursor.getString(2)).append("\n");
                stringBuffer.append("Marks: ").append(cursor.getString(3)).append("\n  ---------- \n");
            }
            ((TextView) findViewById(R.id.tv_display)).setText(stringBuffer.toString());
        }
    }
}