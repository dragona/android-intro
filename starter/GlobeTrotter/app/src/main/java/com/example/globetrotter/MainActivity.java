package com.example.globetrotter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The main activity of the quiz app.
 * The app presents the user with questions to answer, and the score is
 * displayed after all questions have been answered.
 */
public class MainActivity extends AppCompatActivity {
    // Button for True answer
    private Button mTrueButton;
    // Button for False answer
    private Button mFalseButton;
    // TextView to display the question
    private TextView mQuestionTextView;


    private CountDownTimer mTimer;
    private long mTimeRemaining = 10000; //10 seconds for each question
    private TextView mTimeDisplay;


    // An array of questions to be displayed
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.is_australia_a_continent, true),
            new Question(R.string.does_russia_share_a_border_with_kazakhstan, true),
    };
    // The index of the current question
    private int mCurrentIndex = 0;
    // The score of the user's answers
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeDisplay = (TextView) findViewById(R.id.timer_text_view);


        // Bind the TextView to the corresponding UI element
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        // Bind the True button to the corresponding UI element
        mTrueButton = (Button) findViewById(R.id.true_button);
        // Set an OnClickListener for the True button
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        // Bind the False button to the corresponding UI element
        mFalseButton = (Button) findViewById(R.id.false_button);
        // Set an OnClickListener for the False button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // Show the first question
        updateQuestion();
    }

    /**
     * Updates the TextView to show the current question.
     */
    private void updateQuestion() {
        // update the question and options display
        int questionResId = mQuestionBank[mCurrentIndex].getTextResId();
        String question = getString(questionResId);
        mQuestionTextView.setText(question);
        // start the timer
        startTimer();

    }

    /**
     * This method checks the answer of the question and updates the score accordingly.
     *
     * @param userPressedTrue represents the answer selected by the user
     */
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        // check if time has run out before checking the user's answer
        if (mTimeDisplay.getText().equals("Time's up!")) {
            // TODO: display message indicating time has run out
            // move on to the next question
            Toast.makeText(this, "Timer is over", Toast.LENGTH_SHORT).show();

        } else {
            // check the user's answer and update the score
            int messageResId = 0;
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mScore++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

            if (mQuestionBank.length > mCurrentIndex + 1) {
                mCurrentIndex += 1;
                updateQuestion();

            } else {
                Toast.makeText(this, "That is the end of the Quiz.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Score: " + mScore + " out of " + mQuestionBank.length, Toast.LENGTH_SHORT).show();
            }
            // cancel the timer
            cancelTimer();
        }


    }

    private class Question {
        private int mQuestion;
        private boolean mAnswer;

        public Question(int s, boolean b) {
            mQuestion = s;
            mAnswer = b;
        }

        public int getTextResId() {
            return mQuestion;
        }

        public boolean isAnswerTrue() {
            return mAnswer;
        }

    }


    private void startTimer() {
        mTimer = new CountDownTimer(mTimeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeDisplay.setText("Time remaining: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                mTimeDisplay.setText("Time's up!");
                // disable ability to answer the question
                // move on to the next question
            }
        };
        mTimer.start();
    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }


}

