package mg.x261.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/*
TODO:
      - [x] Auto load the audio time when the audio is selected
      - [] Feed media player from source db
      - [x] Time format 00:00 but not 0:0 -> 01:01 but not 1:1
      - [x] Changing current playing position using the seekbar
      - [] Changes icons based on the playing state
      - [] A dedicated place for the Pause button is not needed, we should replace the play with pause when the music is playing
      - [] Use service to play the audio

 */
public class MainActivity extends AppCompatActivity {
    private ImageButton forwardBtn, backwardBtn, pauseBtn, playBtn;
    private MediaPlayer mPlayer;
    private TextView songName, startTime, songTime;
    private SeekBar songProgress;
    private static int oTime = 00, sTime = 00, eTime = 00, fTime = 5000, bTime = 5000;
    private Handler handler = new Handler();
    private boolean pauseVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set volume audio to max
        AudioManager mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int origionalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);


        backwardBtn = findViewById(R.id.btnBackward);
        forwardBtn = findViewById(R.id.btnForward);
        playBtn = findViewById(R.id.btnPlay);
        pauseBtn = findViewById(R.id.btnPause);
        songName = findViewById(R.id.txtSname);
        startTime = findViewById(R.id.txtStartTime);
        songTime = findViewById(R.id.txtSongTime);
        // TODO: song listing source should be updated
        Songs song = new Songs("Live Sayd", "mrsaydalivesccoustique");

        loadAudio(song);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Playing Audio", Toast.LENGTH_SHORT).show();
                mPlayer.start();
                eTime = mPlayer.getDuration();
                sTime = mPlayer.getCurrentPosition();
                if (oTime == 0) {
                    songProgress.setMax(eTime);
                    oTime = 1;
                }
                songTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(eTime),
                        TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime))));
                startTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

                songProgress.setProgress(sTime);
                handler.postDelayed(UpdateSongTime, 100);
                pauseBtn.setEnabled(true);
                playBtn.setEnabled(false);
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
                pauseBtn.setEnabled(false);
                playBtn.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Pausing Audio", Toast.LENGTH_SHORT).show();
            }
        });
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime + fTime) <= eTime) {
                    sTime = sTime + fTime;
                    mPlayer.seekTo(sTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                if (!playBtn.isEnabled()) {
                    playBtn.setEnabled(true);
                }
            }
        });
        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime - bTime) > 0) {
                    sTime = sTime - bTime;
                    mPlayer.seekTo(sTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                if (!playBtn.isEnabled()) {
                    playBtn.setEnabled(true);
                }
            }
        });

        // Handling the seekbar change

        songProgress.setOnSeekBarChangeListener(new songProgressListener());
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            sTime = mPlayer.getCurrentPosition();
            startTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
            songProgress.setProgress(sTime);
            handler.postDelayed(this, 100);
        }
    };

    private void loadAudio(Songs song) {
        songName.setText(song.getTitle());
        String songNameId = song.getAudioFile();
        int songResourceId = getResources().getIdentifier(songNameId, "raw", getPackageName());

        //  mPlayer = MediaPlayer.create(this, R.raw.mrsaydalivesccoustique);
        mPlayer = MediaPlayer.create(this, songResourceId);
        songProgress = (SeekBar) findViewById(R.id.sBar);
        songProgress.setClickable(false);
        pauseBtn.setEnabled(false);

        // Show time as soon as the song is loaded
        eTime = mPlayer.getDuration();
        sTime = mPlayer.getCurrentPosition();

        songTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(eTime),
                TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime))));
        startTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(sTime),
                TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

    }

    private class songProgressListener implements SeekBar.OnSeekBarChangeListener {
        int currentTimeDesiredTime = 0;
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            Log.d("TAG", "onProgressChanged: "+ i );
            currentTimeDesiredTime=i;

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int desired = seekBar.getProgress();
            Log.d("TAG", "onStopTrackingTouch: "+desired);
            if (desired <= eTime) {
                sTime = desired;
                mPlayer.seekTo(sTime);
            } else {
                Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
            }
            if (!playBtn.isEnabled()) {
                playBtn.setEnabled(true);
            }

        }
    }
}