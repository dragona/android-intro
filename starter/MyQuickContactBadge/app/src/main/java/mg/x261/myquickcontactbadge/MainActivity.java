package mg.x261.myquickcontactbadge;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.QuickContactBadge;

public class MainActivity extends AppCompatActivity {

    private long contactId;
    private String lookupKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the values of contactId and lookupKey
        contactId = 1234;
        lookupKey = "ABCD";

        QuickContactBadge quickContactBadge = findViewById(R.id.quick_contact_badge);
        Uri contactUri = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);
        quickContactBadge.assignContactUri(contactUri);
    }
}
