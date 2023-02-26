package mg.x261.activitydemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityListView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview);

        // Define an array of strings containing the data we want to display in the ListView
        String[] items = new String[]{"Item 1", "Item 2", "Item 3"};

        // Create an ArrayAdapter to convert the array of strings into ListView items
        // The ArrayAdapter takes a context, a layout resource for the item, and the data array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items);

        // Get a reference to the ListView in the layout using its unique ID
        ListView myListView = findViewById(R.id.m_listview);

        // Set the adapter for the ListView to the ArrayAdapter we just created
        // This connects the data in the array to the ListView, and handles the display of each item
        myListView.setAdapter(adapter);

        // Set an OnItemClickListener on the ListView
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the text of the clicked item
                String itemText = parent.getItemAtPosition(position).toString();

                // Show a Toast with the text of the clicked item
                Toast.makeText(ActivityListView.this, itemText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
