package cn.edu.cqu.buttontoast;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_layout);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String retreivedInformation = extras.getString("data_to_be_sent");
			TextView display = (TextView) findViewById(R.id.txtLayout);
			display.setText(retreivedInformation);
		}

	}

}
