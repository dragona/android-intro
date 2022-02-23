# Edit text in Android

Define the EditText in your layout file and see the Java code below

```java
package mg.x261.editttext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Connect the buttons and the edit text with this java file
        Button btnClear = (Button) findViewById(R.id.btnClear);
        Button btnToast = (Button) findViewById(R.id.btnGet);
        final EditText myEdittext = (EditText) findViewById(R.id.eTdemo);

        // Set the on click listeners

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the content of the EditText
                myEdittext.setText(null);

            }
        });

        // Set the on click listeners using lambda
        btnToast.setOnClickListener(v -> {
            // Retrieve the string from the edit text
            String userInput = myEdittext.getText().toString();
            // display the string as a toast
            Toast.makeText(getBaseContext(), userInput, Toast.LENGTH_LONG).show();

        });
    }
}
```


Here is the layout

```xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <EditText
        android:id="@+id/eTdemo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:ems="10"
        android:inputType="text"
        android:maxLength="15"
        android:singleLine="true">

        <requestFocus />
    </EditText>

    <Button
        android:id="@+id/btnGet"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:text="clear the EditText" />

    <Button
        android:id="@+id/btnClear"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@+id/btnGet"
        android:layout_alignLeft="@+id/btnGet"
        android:text="Toast the user input" />

</RelativeLayout>
```