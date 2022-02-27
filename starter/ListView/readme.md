# Listview


```java
package mg.x261.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
     * 1-Create a string array to populate the ListView: dummyData
     */

    private final String[] dummyData = {"Dragona", "Dragon", "Long"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * 2-Create a layout to display each item in the list view: list_item.xml
         * 3-Create a layout 'main_activity.xml' that will hold the ListView and
         * connect it to the Java file using setContentView
         */

        setContentView(R.layout.activity_main);

        /*
         * 4-Create an ArrayAdapter 'mAdapter' to display the dummy data
         *      this: the current context
         *      R.layout.list_item: the layout for every single item in the list
         *      R.id.tv_item: The View that will hold the string value from the dummyData
         *      dummyData: the array that contains the dummy data
         */
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tv_item, dummyData);


        /*
         * 5-Create an instance of the ListView (which is inside the main_activity.xml)
         * and set the adapter mAdapter to it by using setAdapter()
         */
        ListView listView = findViewById(R.id.list); //Connect the view list
        listView.setAdapter(mAdapter); //populate the ListView

        //Set the onClick listener to each item of the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplication(), dummyData[i], Toast.LENGTH_LONG).show();
                // Log.d("TAG", dummyData[i]);
            }
        });

    }
}
```


```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">

    <TextView
        android:id="@+id/tv_item"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:layout_weight="1"
        android:text="lipsum demo"
        android:textStyle="bold" />

    <ImageView
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:src="@mipmap/ic_launcher" />


</LinearLayout>
```


```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <ListView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</RelativeLayout>
```