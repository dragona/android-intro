package mg.x261.snackbar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // set the layout for the activity
    }

    public void btnClick(View view) {

        // create a Snackbar with a message and a duration
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.coordinatorLayout),
                "Snackbar With Action",
                Snackbar.LENGTH_LONG);

        // add an action to the Snackbar
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show a Toast when the action is clicked
                Toast.makeText(getApplicationContext(), "Undo action", Toast.LENGTH_SHORT).show();
            }
        });

        // show the Snackbar
        snackbar.show();

    }
}
