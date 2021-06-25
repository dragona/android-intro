package com.studio.android.contentprovideraccess;

/***
 * This applications is used as a proof of concept that
 * when a context provided is set android:exported="true" in the manifest file,
 * it is accessible by other applications.
 *
 * This application will access the content provider from the application
 * ContentProvider Demo that comes with this whole project demo.
 *
 * You first need to install the application Content Provider to see the outcome.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickReadContentProvider(View view) {


        // Retrieve student records
        String URL = "content://com.studio.android.myapplication.StudentsProvider";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");

        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(AccessContentProvider._ID)) +
                                ", " + c.getString(c.getColumnIndex(AccessContentProvider.NAME)) +
                                ", " + c.getString(c.getColumnIndex(AccessContentProvider.GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
