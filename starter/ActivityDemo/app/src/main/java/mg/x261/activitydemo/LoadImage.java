package mg.x261.activitydemo;

// How to integrate openCV with android | java | openCV 4.6.0
// Source: https://www.youtube.com/watch?v=bR7lL886-uc

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.googlecode.leptonica.android.Pix;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.OpenCVLoader;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The LoadImage class is an activity that allows the user to select an image from their device's
 * storage and performs image processing operations on the selected image using the openCV and Tesseract
 * libraries to extract text from the image.
 */
public class LoadImage extends AppCompatActivity {

    // Request code for selecting image
    private static final int PICK_IMAGE_REQUEST = 8888;

    /**
     * Called when the activity is created. Sets the content view.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plate_load_image);
    }

    /**
     * Called when the user taps the "Select Image" button. Launches an intent to select an image.
     *
     * @param view the view that was clicked
     */
    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * Called when the image selection intent is completed. Processes the selected image.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data returned by the intent
     */
    @Override
    /**
     * Handles the result of the image selection activity and performs license plate recognition.
     *
     * @param requestCode the request code sent with the activity result
     * @param resultCode the result code returned by the activity
     * @param data the data returned by the activity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Set up Tesseract for license plate recognition
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        Log.d("TAG", getExternalFilesDir(null).getAbsolutePath());
        tessBaseAPI.init(getExternalFilesDir(null).getAbsolutePath(), "fra");
        // TODO: Need to download and add the corresponding model
        //  from https://github.com/tesseract-ocr/tessdata/blob/main/fra.traineddata
        tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-`~[]{}\\|;:'\"<>,.?/");

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Convert image to grayscale using OpenCV
                Mat grayBitmap = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC1);
                Mat src = new Mat();
                Utils.bitmapToMat(bitmap, src);
                Imgproc.cvtColor(src, grayBitmap, Imgproc.COLOR_RGBA2GRAY);
                Utils.matToBitmap(grayBitmap, bitmap);

                // Display the grayscale image in an ImageView
                ImageView imageView = findViewById(R.id.image_view);
                imageView.setImageBitmap(bitmap);

                // Apply Gaussian blur with kernel size 3x3 and sigma value of 0
                Imgproc.GaussianBlur(grayBitmap, grayBitmap, new Size(3, 3), 0);

                // Apply adaptive thresholding with blockSize 11, C value 2
                Imgproc.adaptiveThreshold(grayBitmap, grayBitmap, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 2);

                // Find contours in the image
                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(grayBitmap, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                // Find the largest contour, which is assumed to be the license plate
                double maxArea = -1;
                int maxAreaIdx = -1;
                for (int idx = 0; idx < contours.size(); idx++) {
                    MatOfPoint contour = contours.get(idx);
                    double area = Imgproc.contourArea(contour);
                    if (area > maxArea) {
                        maxArea = area;
                        maxAreaIdx = idx;
                    }
                }
                MatOfPoint largestContour = contours.get(maxAreaIdx);

                // Crop the license plate region from the image
                Rect boundingRect = Imgproc.boundingRect(largestContour);
                Mat licensePlate = new Mat(grayBitmap, boundingRect);

                // Draw the license plate region on the grayscale image shown on the ImageView
                Imgproc.rectangle(grayBitmap, boundingRect.tl(), boundingRect.br(), new Scalar(255, 0, 0), 2);

                // Convert the grayscale image with the license plate region highlighted to a bitmap
                Bitmap highlightedBitmap = Bitmap.createBitmap(grayBitmap.cols(), grayBitmap.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(grayBitmap, highlightedBitmap);

                // Display the highlighted image in the ImageView
                imageView.setImageBitmap(highlightedBitmap);

                // Convert the license plate region to a bitmap
                Bitmap plateBitmap = Bitmap.createBitmap(licensePlate.cols(), licensePlate.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(licensePlate, plateBitmap);

                // Display the license plate region in an ImageView
                imageView.setImageBitmap(plateBitmap);

                // Convert the license plate region to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                plateBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // Set the license plate image to be recognized by Tesseract
                tessBaseAPI.setImage(plateBitmap);

                // Get the recognized license plate text
                String licensePlateText = tessBaseAPI.getUTF8Text();

                // Display the recognized license plate text to the user
                Toast.makeText(this, "Plate number string: " + licensePlateText.trim(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "OCR result: " + licensePlateText);

                // Clean up Tesseract resources
                tessBaseAPI.end();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called when the activity is resumed.
     * It initializes OpenCV if it hasn't already been initialized and sets up the loader callback.
     *
     * @see OpenCVLoader#initDebug()
     * @see OpenCVLoader#initAsync(String, Context, BaseLoaderCallback)
     * @see LoaderCallbackInterface#SUCCESS
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    /**
     * Callback for loading OpenCV library.
     */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        /**
         * Invoked when the OpenCV library is loaded.
         * @param status the status of the OpenCV library loading
         *
         */
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    // OpenCV loaded successfully
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

}
