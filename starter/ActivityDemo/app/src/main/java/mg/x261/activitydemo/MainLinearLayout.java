package mg.x261.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainLinearLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a new LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        // Set the orientation of the LinearLayout to vertical
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // Create the first TextView
        TextView textView1 = new TextView(this);
        textView1.setText("This is the first TextView");
        // Create the second TextView
        TextView textView2 = new TextView(this);
        textView2.setText("This is the second TextView");
        // Add the TextViews to the LinearLayout
        linearLayout.addView(textView1);
        linearLayout.addView(textView2);
        // Set the content view of the activity to the LinearLayout
        setContentView(linearLayout);
    }
}