
```java

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads  files from the assets folder
 * Copies the files from the assets folder into the SdCard
 * <p>
 * Note: Permission request is needed in order to write in the external storage
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG_LOG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_WRITE_LOCAL_STORAGE = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessAssetsOne();
        accessAssetsTwo("data.txt");
        listFilesInAssets();
        copyAssetsFileIntoSdCard();

        readImageFromAssets();

    }

    /**
     * Accesses and reads a text file from the Assets, set its content in a view
     */
    private void accessAssetsOne() {

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("data.txt")));
            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            ((TextView) findViewById(R.id.tv_top)).setText(stringBuilder.toString());
            Log.d(TAG_LOG, "accessAssetsOne():" + stringBuilder.toString());
        } catch (IOException e) {
            Log.e(TAG_LOG, "Get file from assets, accessAssetsOne(): " + e.toString());
        }

    }

    /**
     * Reads a file from the assets folder and set its content in a view
     *
     * @param fileName file to read from the assets
     */
    private void accessAssetsTwo(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(fileName);

            int size = inputStream.available();

            byte[] bytesBuffer = new byte[size];
            int numberByteInBuffer = inputStream.read(bytesBuffer);
            Log.d(TAG_LOG, "accessAssetsTwo(), numberByteInBuffer: " + numberByteInBuffer);
            inputStream.close();

            // If the buffer has something, set it to the view
            if (numberByteInBuffer > 0) {
                String string = new String(bytesBuffer);
                //Todo: get the previous text and update the view, use AsynchTask
                //String previousText = ((TextView)findViewById(R.id.tv_center)).getText().toString();
                ((TextView) findViewById(R.id.tv_center)).setText(string);

                Log.d(TAG_LOG, "accessAssetsTwo(), bytesBuffer.toString(): " + string);
            }

        } catch (IOException e) {
            Log.e(TAG_LOG, "Get file from assets, accessAssetsTwo() : " + e.toString());
        }


    }

    /**
     * Lists the files that are in the assets folder and open its content
     */

    private void listFilesInAssets() {
        String[] fileNames;
        try {
            fileNames = getAssets().list("");
            for (int i = 0; i < fileNames.length; i++) {
                Log.d(TAG_LOG, "listFilesInAssets(): " + i + " - " + fileNames[i]);
                accessAssetsTwo(fileNames[i]);
            }
        } catch (IOException e) {
            Log.e(TAG_LOG, "listFilesInAssets() : " + e.toString());
        }


    }

    /**
     * Copies the files from the Assets to the the SD card
     */

    private void getAssetAppFolder() {
        String[] fileNames;
        try {
            fileNames = getAssets().list("");
            for (int i = 0; i < fileNames.length; i++) {
                try {
                    InputStream inputStream = getAssets().open(fileNames[i]);
                    copyToDisk(fileNames[i], inputStream);
                } catch (IOException e) {
                    Log.e(TAG_LOG, "getAssetAppFolder() / copyToDisk: " + e.toString());
                }

            }
        } catch (IOException e) {
            Log.e(TAG_LOG, "getAssetAppFolder() : " + e.toString());
        }


    }

    /**
     * Creates a file in the SdCard based on the inputStream provided
     *
     * @param filename    the file name as the copied file
     * @param inputStream for creating the new file
     * @throws IOException
     */
    public void copyToDisk(String filename, InputStream inputStream) throws IOException {
        int size;
        byte[] buffer = new byte[2048];

        //The location for saving the file
        File sdCard = Environment.getExternalStorageDirectory();
        // The external storage
        Log.d(TAG_LOG, "Environment.getExternalStorageDirectory():" + sdCard);
        File directoryDestination = new File(sdCard.getAbsolutePath() + "/backupAssets");

        if (!directoryDestination.exists() || !directoryDestination.isDirectory()) {
            directoryDestination.mkdirs();
        }


        FileOutputStream fileOutputStream = new FileOutputStream(directoryDestination + "/" + filename);
        BufferedOutputStream bufferOut = new BufferedOutputStream(fileOutputStream, buffer.length);

        while ((size = inputStream.read(buffer, 0, buffer.length)) != -1) {
            bufferOut.write(buffer, 0, size);
        }
        bufferOut.flush();
        bufferOut.close();
        inputStream.close();
        fileOutputStream.close();

    }

    /**
     * Insures permissions are granted
     */
    private void copyAssetsFileIntoSdCard() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_LOCAL_STORAGE);

                // MY_PERMISSIONS_REQUEST is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            /* Copy the files that are in the Assets into the SD card */
            try {
                getAssetAppFolder();
            } catch (Exception e) {
                Log.e(TAG_LOG, "getAssetAppFolder()");
            }

            /* END: Copy the files that are in the Assets into the SD card */

        }
    }

    /**
     * Opens the image feilong.jpeg from the assets and use it in a view
     */
    private void readImageFromAssets() {
        InputStream inputStream;
        try {
            inputStream = getAssets().open("img/feilong.jpeg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

```
