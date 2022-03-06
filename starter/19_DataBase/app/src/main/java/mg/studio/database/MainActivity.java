package mg.studio.database;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.database.Cursor;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
    }


    public void btnAdd(View view) {
        String firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
        String marks = ((EditText) findViewById(R.id.marks)).getText().toString();

        boolean dataInserted = databaseHelper.insertData(firstName, lastName, marks);
        if (dataInserted) { // is True
            //Do something
            Log.d(getPackageName(), "Data inserted");

        } else {
            //Show an error
            Log.e(getPackageName(), " Failed inserted data");
        }

        // Clear the edit text
        ((EditText) findViewById(R.id.first_name)).setText("");
        ((EditText) findViewById(R.id.last_name)).setText("");
        ((EditText) findViewById(R.id.marks)).setText("");

        Toast.makeText(this, "Insertion complete!", Toast.LENGTH_LONG).show();

    }

    public void btnReadDb(View view) {
        readingBdContent();
    }

    public void btnDelete(View view) {
        databaseHelper.deleteData();
        Toast.makeText(this, "Delete the DB content", Toast.LENGTH_LONG).show();
        readingBdContent();
    }

    private void readingBdContent() {
        Cursor cursor = databaseHelper.readData();
        if (cursor.getCount() == 0) {
            //The table is empty
            Log.e(getPackageName(), " Reader: empty");
            ((TextView) findViewById(R.id.tv_display)).setText("The database is empty");
        } else {

            Log.e(getPackageName(), " Reading the content");
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()) {
                stringBuffer.append("Id: " + cursor.getString(0) + "\n");
                stringBuffer.append("First name: " + cursor.getString(1) + "\n");
                stringBuffer.append("Last name: " + cursor.getString(2) + "\n");
                stringBuffer.append("Marks: " + cursor.getString(3) + "\n  ---------- \n");
            }
            ((TextView) findViewById(R.id.tv_display)).setText(stringBuffer.toString());
        }
    }
}
