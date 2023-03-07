package mg.x261.mysearchview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the ListView
        SearchView searchView = findViewById(R.id.search_view);

        //Populate the listview
        ListView listView = findViewById(R.id.list_view);
        // Example data for the ListView
        String[] items = {"Apple", "Banana", "Cherry", "Durian", "Elderberry"};

        // Create an ArrayAdapter to display the data in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, items);

        // Set the adapter on the ListView
        listView.setAdapter(adapter);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the ListView based on the search query
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}