package com.example.radiobuttons;

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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	RadioGroup radiogroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Connect the radioGroup
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
		radiogroup.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// When a radio is selected, something will be done
		// if(arg1==R.id.radio0){
		// //Do this
		// Toast.makeText(getBaseContext(), "I love you ",
		// Toast.LENGTH_SHORT).show();
		//
		// }
		// if(arg1==R.id.radio1){
		// //Do this
		// Toast.makeText(getBaseContext(), "I like you",
		// Toast.LENGTH_SHORT).show();
		//
		// }
		// if(arg1==R.id.radio2){
		// //Do this
		// Toast.makeText(getBaseContext(), "Hum...",
		// Toast.LENGTH_SHORT).show();
		//
		// }

		// ////////////////
		switch (arg1) {
		case R.id.radio0:
			Toast.makeText(getBaseContext(), "I love you ", Toast.LENGTH_SHORT)
					.show();

			break;
		case R.id.radio1:
			Toast.makeText(getBaseContext(), "I like you", Toast.LENGTH_SHORT)
					.show();

			break;
		case R.id.radio2:
			Toast.makeText(getBaseContext(), "Hum...", Toast.LENGTH_SHORT)
					.show();

			break;
		default:
			break;
		}

	}
}
