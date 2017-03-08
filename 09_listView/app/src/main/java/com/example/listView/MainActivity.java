package com.example.listView;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	String[] myStringArray = new String[] { "Linear Layout", "Relative layout" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.relative_layout);
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
					showToast(position, ((TextView) view).getText());
					
					break;
				case 1:
					showToast(position, ((TextView) view).getText());				
					
					break;

				default:
					break;
				}
			}

			private void showToast(int position, CharSequence charSequence) {
				// Toast
				Toast.makeText(getBaseContext(), "The element in position "+position+" was clicked, it corresponds to \""+charSequence +"\"", Toast.LENGTH_LONG).show();
				
			}
		});

	}
}
