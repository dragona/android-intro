package mg.studio.listview_demo;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Steps and TODOs:
 * 1- Create a layout for the item list (each item in the list)
 * 2- Create a layout that will contain the listview
 * 3- Create an array which will be used to populate the listview:
 * a dummy content would do create an array adapter, to place
 * the array (the dummy content) in the listview
 * 4- Populate the listview using the array adapter then set the onclick listener
 */

public class MainActivity extends AppCompatActivity {
    // TODO : dummy data
    final String[] countries = {"Afghanistan", "Algeria"};
    final int[] flags = {R.drawable.ic_flag_afghanistan, R.drawable.ic_flag_algeria};

    List<RowItem> rowItems;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO 2 : update the layout for the listview
        setContentView(R.layout.activity_main);

//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_layout, R.id.item_text, arrayDemo);
//
//        // TODO 4: Populate the listview using the array adapter then set the onclick listener


        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < countries.length; i++) {
            RowItem item = new RowItem(flags[i], countries[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.myListView);
        MyListViewAdapter adapter = new MyListViewAdapter(this,
                R.layout.item_layout, rowItems);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener((AdapterView.OnItemClickListener) MainActivity.this);

//        ListView listView = findViewById(R.id.listView);
//        listView.setAdapter(arrayAdapter);
//
//        /*
//         *  Update the title on the action to show the total number of student list
//         */
//        setTitle("The number of students: " + arrayDemo.length);
//
//
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("MY_TAG", String.valueOf(rowItems.get(position).getCountryName()));
            }
        });

    }


}
