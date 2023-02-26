package mg.x261.activitydemo;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.UriMatchers.hasHost;
import static androidx.test.espresso.intent.matcher.UriMatchers.hasPath;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.view.View;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private String[] activityNames = {"ActivityCheckbox", "ActivityDatePicker", "ActivityDatePickerDialog",
            "ActivityListView", "ActivityProgressBar", "ActivityRadioButton", "ActivityRating",
            "ActivityRunnableThreadHandler", "ActivityScrollView", "ActivitySpinner", "ActivitySwitch",
            "ActivityTimePicker", "LoadImage", "MainButton", "MainCalendarView", "MainDialog",
            "MainEditText", "MainImageView", "MainLinearLayout", "MainRelativeLayout", "MainSeekBar",
            "MainTable", "MainTextView", "MainToast"};

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testActivities() {
        for (String activityName : activityNames) {

            // Scroll until we find the next activity
            boolean foundActivity = false;
            do {
                try {
                    onView(ViewMatchers.withText(activityName)).check(matches(isDisplayed()));
                    foundActivity = true;
                } catch (NoMatchingViewException e) {
                    onView(ViewMatchers.withId(R.id.activity_list_view)).perform(ViewActions.swipeUp());
                }
            } while (!foundActivity);
             onView(isRoot()).perform(waitFor(1000));
            onView(ViewMatchers.withText(activityName))
                    .perform(ViewActions.scrollTo(), ViewActions.click());
             onView(isRoot()).perform(waitFor(1000));
            // Press back to go back to the main list
            onView(isRoot()).perform(ViewActions.pressBack());


        }
    }


    private static ViewAction waitFor(long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }



            @Override
            public String getDescription() {
                return "wait for " + millis + "milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }



}
