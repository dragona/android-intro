package com.example.globetrotter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Buttons
    private Button mTrueButton;
    private Button mFalseButton;

    // TextViews
    private TextView mQuestionTextView;
    private TextView mScoreDisplay;
    private TextView mTimeDisplay;

    // ImageViews
    private ImageView mEndGameImage;

    // Timer
    private CountDownTimer mTimer = null;
    private long mTimeRemaining = 10000;

    // Last played sound
    private int lastSongPlayed = -1;


    /**
     * SoundManager class for managing sound effects.
     */
    public static final int SOUND_CORRECT = 1;
    public static final int SOUND_WRONG = 2;
    public static final int SOUND_WIN = 3;
    public static final int SOUND_NOT_GOOD = 4;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;

    /**
     * An array of Question objects representing the quiz questions and their answers.
     */
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.is_australia_a_continent, true),
            new Question(R.string.does_russia_share_a_border_with_kazakhstan, true),
            new Question(R.string.is_equator_in_northern_hemisphere, false),
            new Question(R.string.is_highest_mountain_in_himalayas, true),
            new Question(R.string.does_amazon_river_flow_through_brazil, true),
            new Question(R.string.is_red_sea_between_africa_and_asia, true),
            new Question(R.string.is_capital_of_france_paris, true),
            new Question(R.string.does_nile_flow_into_mediterranean_sea, true),
            new Question(R.string.is_great_barrier_reef_off_coast_of_australia, true),
            new Question(R.string.is_arctic_ocean_located_in_southern_hemisphere, false),
            new Question(R.string.is_dead_sea_between_jordan_and_israel, true),
            new Question(R.string.does_amazon_rainforest_span_over_multiple_countries, true),
            new Question(R.string.is_mount_everest_highest_mountain_in_world, true),
            new Question(R.string.is_sahara_desert_located_in_north_africa, true),
            new Question(R.string.is_pacific_ocean_largest_ocean_in_world, true),
    };

    private int mCurrentIndex = 0;
    private int mScore = 0;

    public int getLastPlayedSound() {
        return lastSongPlayed;
    }


    public Question[] getQuestionBank() {
        return mQuestionBank;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_layout);

        mTimeDisplay = findViewById(R.id.timer_text_view);
        mScoreDisplay = findViewById(R.id.score_text_view);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mEndGameImage = findViewById(R.id.end_game_image_view);
        mTrueButton = findViewById(R.id.true_button);

        initSounds();
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        initTimer();
        updateQuestion();
    }

    private void updateQuestion() {
        int questionResId = mQuestionBank[mCurrentIndex].getTextResId();
        String question = getString(questionResId);
        mQuestionTextView.setText(question);
        startTimer();
    }

    protected void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        if (mTimeDisplay.getText().equals("Time's up!")) {
            showConfirmationDialog("Do you want to try again?");
        } else {
            cancelTimer();
            if (userPressedTrue == answerIsTrue) {
                playSound(SOUND_CORRECT);
                mEndGameImage.setImageDrawable(getDrawable(R.drawable.fireworks));
                mScore++;
            } else {
                playSound(SOUND_WRONG);
                mEndGameImage.setImageDrawable(getDrawable(R.drawable.learnagain));
            }

            if (mQuestionBank.length > mCurrentIndex + 1) {
                mCurrentIndex += 1;
                updateQuestion();
                updateScoreDisplay();
            } else {
                // Define the animation based on the score
                if (mScore > (mQuestionBank.length / 2)) {
                    playSound(SOUND_WIN);
                } else {
                    playSound(SOUND_NOT_GOOD);
                }

                updateScoreDisplay();
                hideViewVisibilityEndGame();
            }
        }
    }


    private void showConfirmationDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked "Yes"
                        restartGame();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked "No"
                        // Do nothing or perform any action you want
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void updateScoreDisplay() {
        String scoreText = "Score: " + mScore + "/" + mQuestionBank.length;
        mScoreDisplay.setText(scoreText);
    }


    private void hideViewVisibilityEndGame() {
        mTrueButton.setVisibility(View.GONE);
        mFalseButton.setVisibility(View.GONE);
        mQuestionTextView.setVisibility(View.GONE);
        mEndGameImage.setVisibility(View.VISIBLE);
    }

    private void disableButtons() {
        mTrueButton.setClickable(false);
        mFalseButton.setClickable(false);
    }

    public int getScore() {
        return mScore;
    }


    protected class Question {
        private int mQuestion;
        private boolean mAnswer;

        public Question(int questionTextResId, boolean answer) {
            mQuestion = questionTextResId;
            mAnswer = answer;
        }

        public int getTextResId() {
            return mQuestion;
        }

        public boolean isAnswerTrue() {
            return mAnswer;
        }
    }

    private void startTimer() {
        if (mTimer != null) {
            mTimer.start();
            Log.d("TAG:", "mTimer" + mTimer);
        } else {
            initTimer();
            mTimer.start();
        }
    }

    private void cancelTimer() {
        Log.d("TAG:", "before cancelled mTimer" + mTimer);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        Log.d("TAG:", "cancelled mTimer" + mTimer);
    }


    /**
     * Initializes the countdown timer for the current question.
     * The timer is set to 10 seconds by default and updates the
     * time display every second. If the timer reaches 0, the "Time's up!"
     * message is displayed and the ability to answer the question is disabled.
     */
    private void initTimer() {
        // Create a new CountDownTimer object with the given time remaining and update interval
        mTimer = new CountDownTimer(mTimeRemaining, 1000) {
            // This method is called every second while the timer is running
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the time display with the remaining time in seconds
                mTimeDisplay.setText("Time : " + millisUntilFinished / 1000 + "s");
                Log.d("ATG:", "Time" + millisUntilFinished / 1000 + "s");
            }

            // This method is called when the timer finishes
            @Override
            public void onFinish() {
                // Update the time display with the "Time's up!" message
                mTimeDisplay.setText("Time's up!");
                // Disable the ability to answer the question
                // Move on to the next question
            }
        };
    }


    private void restartGame() {
        // Reset the index and score
        mCurrentIndex = 0;
        mScore = 0;

        // Reset the score display
        updateScoreDisplay();

        // Show the True and False buttons
        mTrueButton.setVisibility(View.VISIBLE);
        mFalseButton.setVisibility(View.VISIBLE);

        // Show the TextView for the questions
        mQuestionTextView.setVisibility(View.VISIBLE);

        // Hide the fireworks
        mEndGameImage.setVisibility(View.GONE);


        // Enable the True and False buttons
        mTrueButton.setClickable(true);
        mFalseButton.setClickable(true);

        // Reset the timer
        cancelTimer();
        initTimer();

        // Show the first question
        updateQuestion();
    }


    /**
     * Initializes the SoundPool and loads the sound effects.
     */
    private void initSounds() {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(SOUND_CORRECT, soundPool.load(this, R.raw.correct, 1));
        soundPoolMap.put(SOUND_WRONG, soundPool.load(this, R.raw.wrong, 1));
        soundPoolMap.put(SOUND_WIN, soundPool.load(this, R.raw.win, 1));
        soundPoolMap.put(SOUND_NOT_GOOD, soundPool.load(this, R.raw.not_good, 1));
    }

    /**
     * Plays a sound effect with the specified sound ID.
     *
     * @param sound the sound ID to play
     */
    public void playSound(int sound) {

        AudioManager mgr = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;

        soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);

        // Track which sound was played last
        lastSongPlayed = sound;
    }



}

