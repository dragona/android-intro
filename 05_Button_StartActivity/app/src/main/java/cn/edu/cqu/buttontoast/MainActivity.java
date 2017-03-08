package cn.edu.cqu.buttontoast;
/**Introduction to intent and Android Manifest */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Connect the button
		Button btnSend = (Button) findViewById(R.id.btn);

		// Set the onclick listener
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getBaseContext(),SecondActivity.class);
				startActivity(intent);
			}
		});
	}

}
