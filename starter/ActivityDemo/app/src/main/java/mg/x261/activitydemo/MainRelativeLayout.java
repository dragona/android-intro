package mg.x261.activitydemo;


/**
 * we create an instance of the RelativeLayout class and set its width
 * and height to match_parent. We then set padding to 16dp on all sides.
 *
 * We create each child view using the EditText and Button classes,
 * set their properties and add them to the layout. We use setHint()
 * to set the hint text and setText() to set the text for the Button.
 *
 * To position the child views relative to each other, we use the
 * RelativeLayout.LayoutParams class to set the layout parameters and
 * the addRule() method to position the views relative to other views.
 * Finally, we set the content view of the activity to the RelativeLayout
 * using the setContentView() method.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainRelativeLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new RelativeLayout
        RelativeLayout layout = new RelativeLayout(this);

        // Set the layout's width and height to match_parent
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Set padding for the layout
        layout.setPadding(16, 16, 16, 16);

        // Create a new EditText for the 'To' field
        EditText editTextTo = new EditText(this);

        // Set the ID for the 'To' field
        editTextTo.setId(R.id.editTextTo);

        // Set the layout parameters for the 'To' field
        editTextTo.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Set the hint text for the 'To' field
        editTextTo.setHint(R.string.to);

        // Add the 'To' field to the layout
        layout.addView(editTextTo);

        // Create a new EditText for the 'Subject' field
        EditText editTextSubject = new EditText(this);

        // Set the ID for the 'Subject' field
        editTextSubject.setId(R.id.editTextSubject);

        // Set the layout parameters for the 'Subject' field
        RelativeLayout.LayoutParams subjectParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Position the 'Subject' field below the 'To' field
        subjectParams.addRule(RelativeLayout.BELOW, R.id.editTextTo);
        editTextSubject.setLayoutParams(subjectParams);

        // Set the hint text for the 'Subject' field
        editTextSubject.setHint(R.string.subject);

        // Add the 'Subject' field to the layout
        layout.addView(editTextSubject);

        // Create a new EditText for the message field
        EditText editTextMessage = new EditText(this);

        // Set the ID for the message field
        editTextMessage.setId(R.id.editTextMessage);

        // Set the layout parameters for the message field
        RelativeLayout.LayoutParams messageParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        // Position the message field above the send button and below the 'Subject' field
        messageParams.addRule(RelativeLayout.ABOVE, R.id.buttonSend);
        messageParams.addRule(RelativeLayout.BELOW, R.id.editTextSubject);
        editTextMessage.setLayoutParams(messageParams);

        // Set the gravity for the message field
        editTextMessage.setGravity(android.view.Gravity.TOP);

        // Set the hint text for the message field
        editTextMessage.setHint(R.string.message);

        // Add the message field to the layout
        layout.addView(editTextMessage);

        // Create a new send button
        Button buttonSend = new Button(this);

        // Set the ID for the send button
        buttonSend.setId(R.id.buttonSend);

        // Set the layout parameters for the send button
        RelativeLayout.LayoutParams sendParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Align the send button to the parent's end and bottom
        sendParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        sendParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonSend.setLayoutParams(sendParams);

        // Set the text for the send button
        buttonSend.setText(R.string.send);

        // Add the send button to the layout
        layout.addView(buttonSend);

        setContentView(layout);

    }
}