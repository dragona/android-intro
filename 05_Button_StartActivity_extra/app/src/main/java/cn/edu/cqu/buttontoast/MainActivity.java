package cn.edu.cqu.buttontoast;

/**Introduction to intent and Android Manifest */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		final EditText userInput =findViewById(R.id.et_input);
		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String information = userInput.getText().toString();
				//Todo: handle the case where information is empty
				Intent intent = new Intent(getApplicationContext(),
						SecondActivity.class);
				intent.putExtra("shared_data", information);
				startActivity(intent);
			}
		});
	}

}
