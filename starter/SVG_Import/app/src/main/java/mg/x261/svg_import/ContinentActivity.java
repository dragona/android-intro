package mg.x261.svg_import;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

public class ContinentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent);

        String[] continents = new World().getContinents();
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                this,
                R.layout.item_continent,
                R.id.tvContinent,
                continents
        );

        ListView myListView = findViewById(R.id.listContinent);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                //TODO: Open the question
                Intent intent = new Intent(ContinentActivity.this, GameActivity.class);
                intent.putExtra("continent_selected", itemPosition);
                startActivity(intent);
                finish();

            }
        });


    }
}
