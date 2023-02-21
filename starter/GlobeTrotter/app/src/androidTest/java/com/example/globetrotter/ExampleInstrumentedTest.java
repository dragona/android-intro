package com.example.globetrotter;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.os.SystemClock;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    boolean[] answers = new boolean[]{true, true, false, true, true, true, true, true, true, false, true, true, true, true, true};


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test that all quiz questions are answered correctly.
     * The score text view updates correctly for each question.
     */
    @Test
    public void testAllQuestionsWin() {
        int score = 0;
        for (int i = 0; i < answers.length; i++) {
            boolean answer = answers[i];

            // Perform UI interactions and assertions on a background thread using runOnUiThread
            onView(answer ? withId(R.id.true_button) : withId(R.id.false_button)).perform(click());
            score = i + 1;
            onView(withId(R.id.score_text_view)).check(matches(withText("Score: " + score + "/15")));


            // Delay for 1 second to allow the UI to update
            SystemClock.sleep(1000);
        }
    }


    @Test
    public void testAllQuestionsLose() {
        boolean[] answersToTest = new boolean[]{true, true, false, true, true, true, true, true, false, false, false, false, false, false, false};

        int score = 0;
        for (int i = 0; i < answersToTest.length; i++) {
            boolean answer = answersToTest[i];

            // Perform UI interactions and assertions on a background thread using runOnUiThread
            onView(answer ? withId(R.id.true_button) : withId(R.id.false_button)).perform(click());
            if (answers[i] == answer) {
                score++;
            }
            System.out.print(score);

            onView(withId(R.id.score_text_view)).check(matches(withText("Score: " + score + "/15")));

            // Delay for 1 second to allow the UI to update
            SystemClock.sleep(1000);
        }
    }

    /**
     * Test that the score text view is not incremented when an incorrect answer is selected.
     */
    @Test
    public void testScoreNotIncrementedOnIncorrectAnswer() {
        onView(withId(R.id.false_button)).perform(click());
        onView(withId(R.id.score_text_view)).check(matches(withText("Score: 0/15")));
    }

    /**
     * Test that the correct sound effect is played when the answer is correct.
     */
    @Test
    public void testCorrectSoundEffectOnCorrectAnswer() {
        waitFor(1000);

        ActivityScenario<MainActivity> scenario = activityScenarioRule.getScenario();
        scenario.onActivity(activity -> {
            activity.checkAnswer(true);
            assertEquals(MainActivity.SOUND_CORRECT, activity.getLastPlayedSound());
        });
    }

    /**
     * Test that the incorrect sound effect is played when the answer is incorrect.
     */
    @Test
    public void testIncorrectSoundEffectOnIncorrectAnswer() {
        waitFor(1000);

        ActivityScenario<MainActivity> scenario = activityScenarioRule.getScenario();
        scenario.onActivity(activity -> {
            activity.checkAnswer(false);
            assertEquals(MainActivity.SOUND_WRONG, activity.getLastPlayedSound());
        });
    }

    /**
     * Wait for the specified number of milliseconds.
     *
     * @param millis the number of milliseconds to wait.
     */
    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
