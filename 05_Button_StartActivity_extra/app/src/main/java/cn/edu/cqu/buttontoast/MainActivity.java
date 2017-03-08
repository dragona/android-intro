package cn.edu.cqu.buttontoast;

/**Introduction to intent and Android Manifest */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnSend = (Button) findViewById(R.id.btn);
		final EditText userInput = (EditText) findViewById(R.id.eTinput);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String information = userInput.getText().toString();
				Intent intent = new Intent(getApplicationContext(),
						SecondActivity.class);
				intent.putExtra("data_to_be_sent", information);
				startActivity(intent);
			}
		});
	}

}
