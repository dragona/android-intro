package mg.studio.android.survey;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    String SPACER = "_";
    String student_identifier = "20170000";
    String TIME_STAMP = timeStamp();
    String content = "This is the content : " + student_identifier + "\n" + TIME_STAMP;
    String file_name = student_identifier + SPACER + TIME_STAMP;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "Student IDENTIFIER: " + student_identifier);
        Log.d("TAG", "Unique content: " + TIME_STAMP);
        Log.d("TAG", "File name: " + TIME_STAMP);
        Log.d("TAG", " START - save to storage ");
        saveExternalStorageGetExternalFilesDir();
        Log.d("TAG", " END - save to storage ");


        // START - Trace log in a TextView
        try {
            Process process = Runtime.getRuntime().exec("logcat -d -s TAG");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
                log.append("\n");
                log.append("--------------------");
                log.append("\n");
            }
            TextView tv = findViewById(R.id.display);
            tv.setText(log.toString());
        } catch (IOException e) {
            // Handle Exception

        }

        // END - Trace log in a TextView


    }

    public String timeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date) + ".txt";

    }

    /**
     * Self app uninstall
     * Permission <uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />
     *
     * @param view
     */
    public void btnUninstall(View view) {
        // remove the data previously written
        File fileObject = getExternalFilesDir(null);
        fileObject.delete();
        // Uninstall the app
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:mg.studio.android.survey"));
        this.finish();
        startActivity(intent);
    }

    /**
     * The saving method should be placed below
     */

    /**
     * Location: The exact location of where your files can be saved might vary across devices.
     * One example location is on an AVD with API 29
     * /storage/emulated/0/Android/data/mg.studio.android.survey/files
     * Permission: Not needed for external storage when your app is used on devices
     * that run Android 4.4 (API level 19) or higher
     */
    private void saveExternalStorageGetExternalFilesDir() {

        /**
         *  check availability  and  not write protection status
         */

        String en = Environment.getExternalStorageState();
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(System.currentTimeMillis());
            String fileName = simpleDateFormat.format(date) + ".txt";
            // Get app specific location for external storage
            // The exact location of where your files can be saved might vary across devices.
            // For this reason, don't use hard-coded file paths.
            String external_storage_path = getExternalFilesDir(null).getAbsolutePath();
            Log.d("TAG", "external_storage_path:  " + external_storage_path);

            // Create the external directory
            File abstract_file_dir = new File(external_storage_path);
            Log.d("TAG", "abstract_file_dir:  " + abstract_file_dir);

            // Ensure external directory exists
            if (!abstract_file_dir.exists()) {
                Log.e("TAG", "!abstract_file_dir.exists(): directory creation failed");
                if (!abstract_file_dir.mkdirs()) {
                    return;
                }
            }

            // create a file within the external directory
            File file = new File(external_storage_path + File.separator + file_name);
            Log.d("TAG", "File created: " + file);

            try {
                if (!file.exists()) {
                    if (!file.createNewFile()) {
                        Log.e("TAG", "!file.createNewFile(): file creation failed");
                        return;
                    }
                }
                // Use PrintSteam to write (print) the content inside the file
                PrintStream outputStream = new PrintStream(new FileOutputStream(file));
                outputStream.print(content);
                outputStream.close();
                Log.d("TAG", "OutputStream used for writing the content inside the file.");
            } catch (Exception e) {
                Log.e("TAG", "Error : " + e);
                return;
            }
        } else {
            String error = "Error : External storage not available.";
            Log.e("TAG", error);
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }


}
