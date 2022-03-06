package mg.x261.listviewrefreshlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>(
            Arrays.asList("Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the reference of and ListView
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this,
                R.layout.item_listview,
                R.id.tv_item,
                arrayList);
        listView.setAdapter(arrayAdapter);


    }


    public void btnRefresh(View view) {
        // Shuffling the arraylist items on the basis of system time
        Collections.shuffle(arrayList, new Random(System.currentTimeMillis()));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_listview, R.id.tv_item, arrayList);
        listView.setAdapter(arrayAdapter);

    }
}