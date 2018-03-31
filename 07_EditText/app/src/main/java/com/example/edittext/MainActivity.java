package com.example.edittext;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
 

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Connect the buttons and the edit text with this java file
		Button btnClear = (Button) findViewById(R.id.btnClear);
		Button btnToast = (Button) findViewById(R.id.btnGet);
		final EditText myEdittext = (EditText) findViewById(R.id.eTdemo);

		// Set the on click listeners

		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Clear the content of the EditText
				myEdittext.setText(null);

			}
		});

		btnToast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Retrieve the string from the edit text
				String userInput = myEdittext.getText().toString();
				
				// display the string as a toast
				Toast.makeText(getBaseContext(), userInput, Toast.LENGTH_LONG).show();

			}
		});
	}
}
