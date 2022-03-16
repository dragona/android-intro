package mg.x261.recordvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private String recordFile;
    private TextView filenameText;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filenameText = findViewById(R.id.display);
    }

    public void startRecording() {

        //Get app external directory path
        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();

        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
        Date now = new Date();

        //initialize filename variable with date and time at the end to ensure the new file wont overwrite previous file
        recordFile = "Recording_" + formatter.format(now) + ".3gp";

        filenameText.setText("Recording, File Name : " + recordFile);

        //Setup Media Recorder for recording
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        Log.d("TAG", "recordPath" + recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Start Recording
        mediaRecorder.start();
    }


    private boolean checkPermissions() {
        //Check permission
        String recordPermission = Manifest.permission.RECORD_AUDIO;
        if (ActivityCompat.checkSelfPermission(this, recordPermission) == PackageManager.PERMISSION_GRANTED) {
            //Permission Granted
            return true;
        } else {
            //Permission not granted, ask for permission
            int PERMISSION_CODE = 23;
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (isRecording) {
            stopRecording();
        }
    }

    private void stopRecording() {

        findViewById(R.id.btnStartRecording).setVisibility(View.VISIBLE);
        //Change text on page to file saved
        filenameText.setText(String.format("Recording Stopped, File Saved : %s", recordFile));

        //Stop media recorder and set it to null for further use to record new audio
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void btnStartRecording(View view) {
        view.setVisibility(View.GONE);
        findViewById(R.id.btnStopRecording).setVisibility(View.VISIBLE);
        if (isRecording) {
            //Stop Recording
            stopRecording();
            isRecording = false;
        } else {
            //Check permission to record audio
            if (checkPermissions()) {
                //Start Recording
                startRecording();
                isRecording = true;
            }
        }
    }

    public void btnStopRecording(View view) {
        view.setVisibility(View.GONE);
        ((Button) findViewById(R.id.btnStartRecording)).setVisibility(View.VISIBLE);
        stopRecording();
    }
}