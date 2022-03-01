package mg.x261.listview_image_and_text;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO 1: prepare the drawables
    // TODO 2: prepare the data
    final String[] countries = {"Afghanistan", "Algeria"};
    final int[] flags = {R.drawable.ic_flag_afghanistan, R.drawable.ic_flag_algeria};


    List<RowItem> rowItems;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < countries.length; i++) {
            RowItem item = new RowItem(flags[i], countries[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.myListView);
        MyListViewAdapter adapter = new MyListViewAdapter(this,
                R.layout.item_layout, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, l) ->
                Log.d("MY_TAG", String.valueOf(rowItems.get(position).getCountryName())));
    }
}