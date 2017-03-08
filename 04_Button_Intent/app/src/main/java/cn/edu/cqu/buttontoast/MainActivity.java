package cn.edu.cqu.buttontoast;
/*An intent is an abstract description of an operation to be performed.
Read more at : http://developer.android.com/reference/android/content/Intent.html
*/
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
		Button btnSend = (Button) findViewById(R.id.btnToast);

		// Set the onclick listener
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//Create an intent
				Intent openUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cqu.edu.cn"));
				//Start the intent
				startActivity(openUrl);
			}
		});
	}

}
