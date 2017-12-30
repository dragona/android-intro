package com.example.layouts;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	String[] myStringArray = new String[] { "Linear Layout", "Relative layout" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				myStringArray));
		
		//Get the activity`s listView, use getListView()
		ListView myListView = getListView();
		
		//Handle click events
		 
		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    // Do something based on user clicked
				switch (position) {
				case 0:
					//Start Activity Linearlayout
					startActivity(new Intent(MainActivity.this,ActivityLinearLayout.class));
					break;
				case 1:
					//Start Activity RelativeLayout				
					startActivity(new Intent(MainActivity.this,ActivityRelativelayout.class));
					break;

				default:
					break;
				}
			}
		});
	}
}
