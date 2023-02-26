package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityScrollView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scrollview);

        TextView myTextView = findViewById(R.id.long_text_textview);
        StringBuilder loremIpsum = generateLoremIpsum();
        myTextView.setText(loremIpsum.toString());



    }

    /**
     * Generates a StringBuilder containing a long text of "Lorem Ipsum" using a loop and StringBuilder.
     *
     * @return The generated StringBuilder containing the long text of "Lorem Ipsum".
     */
    public StringBuilder generateLoremIpsum() {
        int numCopies = 100;
        StringBuilder loremIpsum = new StringBuilder();
        for (int i = 0; i < numCopies; i++) {
            loremIpsum.append("Lorem ipsum dolor sit amet, consectetur adipiscing elit. ");
        }
        return loremIpsum;
    }

}
