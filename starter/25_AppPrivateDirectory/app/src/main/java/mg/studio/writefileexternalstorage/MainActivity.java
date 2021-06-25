/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package mg.studio.writefileexternalstorage;

import android.content.Context;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Create a file, write in a text and save it inside a private directory of the app (external storage)
 * Writing in the private directory of the application does not require specific permission
 */

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        String timeStamp /* use as file name */ = String.valueOf(calendar.getTimeInMillis());
        String text_to_save = "Created on " + timeStamp;

        //Save the text_to_save in a file inside the private Directory of the application
        saveFileInPrivateDirectory(this, timeStamp + ".txt", text_to_save);

    }

    /**
     * @param context    the current context.
     * @param folderName represents the folder name to use.
     * @return FileSystem object representing the platform's local file system.
     */
    public File getPrivateStorageDirectory(Context context, String folderName) {

        // Get the directory for the app's private downloads directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), folderName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    /**
     * Creates a file, writes in a string and saves it in the private Storage
     * directory of the application. Writing the private directory of the application
     * does not require specific permissions.
     * Location may vary but here is what I have while testing this:
     * /storage/emulated/0/Android/data/mg.studio.writefileexternalstorage/files/Download/directory
     *
     * @param context   the current context
     * @param sFileName the file name to create to save the string
     * @param sBody     string to save in the text file
     */
    public void saveFileInPrivateDirectory(Context context, String sFileName, String sBody) {

        String folderName = "directory";
        try {
            File root = getPrivateStorageDirectory(context, folderName);
            File file = new File(root, sFileName);
            FileWriter writer = new FileWriter(file);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Log.d(LOG_TAG, "Saved: " + root.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "saveFileInPrivateDirectory(): " + e);
        }
    }
}
