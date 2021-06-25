package mg.studio.android.flashlight2;

/*
 * This is a flashlight demo that uses camera2 based on
 * https://developer.android.com/reference/android/hardware/camera2/package-summary
 * <p>
 * Original code was from https://www.androidtutorialpoint.com/basics/learn-by-doing/flash-light-application/
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton mTorchOnOffButton;
    boolean isTorchOn;
    private CameraManager mCameraManager;
    private String mCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("FlashLightActivity", "onCreate()");
        mTorchOnOffButton = findViewById(R.id.button_on_off);
        isTorchOn = false;

        /*
         * Checking whether flash is available
         */
        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            /*
             * Show a dialog is the flashlight is not available
             */
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Oups");
            alert.setMessage("Flashlight is not available. Close the app!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                // closing the application
                finish();
                System.exit(0);
            });
            alert.show();
            // end everything here.
            return;
        }

        /*
         * Initiate possible manipulation of a flash light
         */
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        mTorchOnOffButton.setOnClickListener(v -> {
            try {
                if (isTorchOn) {
                    turnOffFlashLight();
                    isTorchOn = false;
                } else {
                    turnOnFlashLight();
                    isTorchOn = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method for turning the flashlight on
     */
    private void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
                playOnOffSound();
                mTorchOnOffButton.setImageResource(R.drawable.light_on);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playOnOffSound() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.switch_sound);
        mp.setOnCompletionListener(MediaPlayer::release);
        mp.start();
    }


    /**
     * Method for turning the flash light off
     */
    private void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
                playOnOffSound();
                mTorchOnOffButton.setImageResource(R.drawable.light_off);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

