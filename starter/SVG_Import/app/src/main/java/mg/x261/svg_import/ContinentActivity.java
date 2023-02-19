//This code represents the `ContinentActivity` class of an Android app that deals with continents
//The class extends AppCompatActivity to use the Androidx library.

package mg.x261.svg_import;

//import required classes from the Android framework
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import the AppCompatActivity class from the Androidx library
import androidx.appcompat.app.AppCompatActivity;

public class ContinentActivity extends AppCompatActivity {
    
    //This method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //This sets the layout file to be used for the activity
        setContentView(R.layout.activity_continent);

        //Get the continents from the World class
        String[] continents = new World().getContinents();
        
        //Create an adapter to bind the continent data to the ListView
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                this, //context
                R.layout.item_continent, //layout file
                R.id.tvContinent, //text view id in the layout file
                continents //data to be displayed
        );

        //Get a reference to the ListView in the layout file
        ListView myListView = findViewById(R.id.listContinent);
        
        //Set the adapter to the ListView
        myListView.setAdapter(mAdapter);
        
        //Set an item click listener to the ListView
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                //Create an Intent to start the GameActivity
                Intent intent = new Intent(ContinentActivity.this, GameActivity.class);
                
                //Put the selected item position as extra data to be passed to the GameActivity
                intent.putExtra("continent_selected", itemPosition);
                
                //Start the GameActivity
                startActivity(intent);
                
                //Finish the current activity
                finish();

            }
        });
    }
}
