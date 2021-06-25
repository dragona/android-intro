package com.example.radiobuttonsclick;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	RadioGroup radiogroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Connect the radioGroup
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup);

		// Connect the button
		Button btn = (Button) findViewById(R.id.btn);
		// Adding a listener
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// retrieve the radio button that was selected
				int radioSelected = radiogroup.getCheckedRadioButtonId();

				// get the text associated with that radioId
				RadioButton selected = (RadioButton) findViewById(radioSelected);
				// Show a toast
				/**
				 * See the difference between the two toasts below
				 **/
				// Toast.makeText(getBaseContext(), selected+"",
				// Toast.LENGTH_SHORT).show();

				Toast.makeText(getBaseContext(), selected.getText(),
						Toast.LENGTH_SHORT).show();

			}
		});

	}

}
