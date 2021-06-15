package mg.studio.usernamefinal;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect the button
        Button btnSend = findViewById(R.id.mBtn);
        //Connect the EditText
        final EditText userInput = findViewById(R.id.eTuserInput);

        //Set the onclick listener
        /*
         * Display a toast when the button is pressed
         * Toast to be display should not be the same
         * if the user did not write her name
         * */
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Get the user input from the edit text
                String theUserInput = userInput.getText().toString();


                if (theUserInput.isEmpty()) {
                    Toast.makeText(getBaseContext(), "The user did not input anything", Toast.LENGTH_LONG).show();
                } else {
                    //if the user wrote her name.
                    Toast.makeText(getBaseContext(), "The user`s name is : \n   " + theUserInput, Toast.LENGTH_LONG).show();

                    /***
                     * Student number:
                     * Name (Chinese):
                     * Name (English):
                     */
                    //TODO 1: clear the content

                    //TODO 2: Start a new activity that shows the user input in a TextView
                    // the class file name for the second activity should be DisplayActivity.java

                }

                // Hide the keyboard after when the button is pressed
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


            }
        });
    }
}
