package cn.edu.cqu.buttontoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Connect the button
		Button btnSend = (Button) findViewById(R.id.btnToast);

		// Set the onclick listener
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Toast
				Toast.makeText(getBaseContext(),"You pressed the button", Toast.LENGTH_LONG).show();

			}
		});
	}

}
