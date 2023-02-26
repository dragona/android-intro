package mg.x261.activitydemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRating extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rating);

        RatingBar myRatingBar = findViewById(R.id.ratingBar);
        myRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Handle changes to the RatingBar's value here
                // The "rating" parameter contains the new rating value
                Log.d("TAG", String.valueOf(rating));
            }
        });

    }
}
