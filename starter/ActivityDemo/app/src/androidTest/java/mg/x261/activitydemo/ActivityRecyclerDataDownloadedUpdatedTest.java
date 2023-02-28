package mg.x261.activitydemo;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import android.content.Context;

@RunWith(AndroidJUnit4.class)
public class ActivityRecyclerDataDownloadedUpdatedTest {
    @Rule
    public ActivityScenarioRule<ActivityRecyclerDataDownloadedUpdated> activityScenarioRule =
            new ActivityScenarioRule<>(ActivityRecyclerDataDownloadedUpdated.class);


//    @Test
//    public void testFirstPageDataLoaded() {
//        ActivityScenario<ActivityRecyclerDataDownloadedUpdated> scenario = ActivityScenario.launch(ActivityRecyclerDataDownloadedUpdated.class);
//        scenario.onActivity(new ActivityScenario.ActivityAction<ActivityRecyclerDataDownloadedUpdated>() {
//            @Override
//            public void perform(ActivityRecyclerDataDownloadedUpdated activity) {
//                RecyclerView recyclerView = activity.findViewById(R.id.my_recycler_view_for_downloaded_data);
//
//                // Wait for the first page of data to be loaded
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // Check if the first data from page 1 is fully loaded
//                ActivityRecyclerDataDownloadedUpdated.MyAdapter adapter = (ActivityRecyclerDataDownloadedUpdated.MyAdapter) recyclerView.getAdapter();
//                ActivityRecyclerDataDownloadedUpdated.DataObject firstDataObject = adapter.getDataObjects().get(0);
//                assertEquals("Afghanistan", firstDataObject.getCountry());
//                assertEquals("Kabul", firstDataObject.getCapital());
//            }
//        });
//    }
    @Test
    public void testLastPageDataLoaded() {
        // Launch the activity
        ActivityScenario<ActivityRecyclerDataDownloadedUpdated> scenario = ActivityScenario.launch(ActivityRecyclerDataDownloadedUpdated.class);

        // Wait for the activity to be created and the first page of data to be loaded
        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.my_recycler_view_for_downloaded_data);

            // Wait for the last page of data to be loaded
            int totalNumberOfItems = 0;
            int totalNumberOfPages = 0;
            while (true) {
                // Check if the data has been fully loaded
                ActivityRecyclerDataDownloadedUpdated.MyAdapter adapter = (ActivityRecyclerDataDownloadedUpdated.MyAdapter) recyclerView.getAdapter();
                totalNumberOfItems = adapter.getItemCount();
                totalNumberOfPages = activity.getTotalNumberOfPages();
                if (totalNumberOfItems == totalNumberOfPages * ActivityRecyclerDataDownloadedUpdated.ITEMS_PER_PAGE) {
                    break;
                }

                // Continuously scroll down to trigger data loading
                CountingIdlingResource idlingResource = new CountingIdlingResource("scroll");
                Espresso.registerIdlingResources(idlingResource);
                int finalTotalNumberOfItems = totalNumberOfItems;
                recyclerView.postDelayed(() -> {
                    recyclerView.smoothScrollToPosition(finalTotalNumberOfItems - 1);
                    idlingResource.decrement();
                }, 1000);
                idlingResource.increment();
            }

            // Check if the last page of data is fully loaded
            ActivityRecyclerDataDownloadedUpdated.MyAdapter adapter = (ActivityRecyclerDataDownloadedUpdated.MyAdapter) recyclerView.getAdapter();
            ActivityRecyclerDataDownloadedUpdated.DataObject lastDataObject = adapter.getDataObjects().get(totalNumberOfItems - 1);
            assertEquals("Romania", lastDataObject.getCountry());
            assertEquals("Bucharest", lastDataObject.getCapital());
        });
    }



}
