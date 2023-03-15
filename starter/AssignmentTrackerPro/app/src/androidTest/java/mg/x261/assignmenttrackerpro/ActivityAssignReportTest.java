package mg.x261.assignmenttrackerpro;



import org.junit.Rule;
import org.junit.Test;

import androidx.recyclerview.widget.RecyclerView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class ActivityAssignReportTest {

    @Rule
    public ActivityScenarioRule<ActivityAssignReport> activityScenarioRule =
            new ActivityScenarioRule<>(ActivityAssignReport.class);

    @Test
    public void testSpinnerView() throws InterruptedException {
        // Wait for the data to load
        Thread.sleep(5000);

        // Scroll to the bottom of the RecyclerView
        activityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.reportRecyclerView);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                int itemCount = adapter.getItemCount();
                recyclerView.scrollToPosition(itemCount - 1);
            }
        });

        // Wait for 2 seconds after the data is fully loaded
        Thread.sleep(2000);




    }
}
