package mg.x261.happybirthday;

/**
 * Get a screenshot of the ViewGroup that is defined by the view ID passed to process()
 * Handles the permission requests and file storing
 *
 * IMPORTANT to add in the manifest file
 *
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 *     <application
 *         android:requestLegacyExternalStorage="true"
 *
 *
 * This code is based on
 * https://stackoverflow.com/a/16109978
 * https://stackoverflow.com/questions/2661536/how-to-programmatically-take-a-screenshot-on-android
 */

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

public class HelperScreenShot {

    // Requesting runtime permissions
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void process(View view, Activity context) throws IOException {
        // Make sure permission is granted

        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    context,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {

            //
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                    view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            //save the bitmap;
            SaveBitmap(bitmap, context);
        }
    }

    // Saving the file
    private void SaveBitmap(Bitmap bitmapFile, Activity context) throws IOException {
        // Assume block needs to be inside a Try/Catch block.
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        Date currentTime = Calendar.getInstance().getTime();
        String uniqueIdentifier = currentTime.toString();
        File file = new File(path, "screenshot_" + uniqueIdentifier + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
        fOut = new FileOutputStream(file);

        // obtaining the Bitmap
        bitmapFile.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        fOut.flush(); // Not really required
        fOut.close(); // do not forget to close the stream

        MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
    }


}
