# Pass date between activities.

An intent can be used for starting another activity. At the same time, it can be used for passing data between activities.

In this demo app, we have two activities; where the first activity has an Edittext to receive the user input, a button to trigger the extraction of the user input from the edittext, and which is also used for initiating the
use of intent to pass data from the current activity to the second.

![Demo](display/demo.gif)


To follow along, you can create a new project. Update the ```MainActivity.java``` file to have the following content.

```java

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userInput = findViewById(R.id.et_input);
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String information = userInput.getText().toString();
                //Todo: handle the case where information is empty
                Intent intent = new Intent(getApplicationContext(),
                        SecondActivity.class);
                intent.putExtra("shared_data", information);
                startActivity(intent);
            }
        });
    }

}

```

You must have noticed that we are referring to ```et_input```, thus we need to update the layout file.

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/darker_gray"
    android:padding="8dp">


    <EditText
        android:id="@+id/et_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginLeft="26dp"
        android:layout_marginRight="26dp"
        android:layout_marginTop="25dp"
        android:background="@android:color/white"
        android:hint="write something"
        android:inputType="text"
        android:padding="8dp">

        <requestFocus />
    </EditText>

    <Button
        android:id="@+id/btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerInParent="true"
        android:background="@android:color/black"
        android:text="@string/btn_text"
        android:textColor="@android:color/white" />

</RelativeLayout>
```



It is not time to create the second Activity

```java

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

       // todo : get the extras
    }

}

```

And we need to update this ```SecondActivity``` so that it can extract the data from the ```MainActivity```
Let`s add the following during the ```onCreate```

```java
 Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String retrievedInformation = extras.getString("shared_data");
            // todo: do something with the retrievedinformation
        }

```


Our final ```SecondActivity``` looks like this

```java

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Bundle extras = getIntent().getExtras();
               if (extras != null) {
                   String retrievedInformation = extras.getString("shared_data");
                   // todo: do something with the retrieved information
               }
    }

}

```

So far so good. We now need to create the ```sexond_layout.xml``` which we connect to the ```SecondActivity.java```


```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="10dp"
    android:orientation="vertical" >

    <TextView
        android:id="@+id/txtLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="@string/txt_second_layout"
        android:textAppearance="?android:attr/textAppearanceMedium" />

</LinearLayout>
```

We will use the ```TextView``` with the ```id``` ```txtLayout``` to display the data extracted from the ```MainActivity``` and 
passed to the  ```SecondActivity```

Thus we can add the following after we get the data in the ```SecondActivity```

```java

TextView display = findViewById(R.id.txtLayout);
display.setText(retrievedInformation);
```

And our final ```SecondActivity``` code looks like this:


```java

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Bundle extras = getIntent().getExtras();
               if (extras != null) {
                   String retrievedInformation = extras.getString("shared_data");
                   TextView display = findViewById(R.id.txtLayout);
                   display.setText(retrievedInformation);
               }
    }

}

```

It is good to note that for each Activity you have to ensure that it is declared in the ```AndroidManifest.xml``` file

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="cn.edu.cqu.buttontoast"
    android:versionCode="1"
    android:versionName="1.0" >

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name="cn.edu.cqu.buttontoast.MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="cn.edu.cqu.buttontoast.SecondActivity"
            android:label="@string/app_name" >
        </activity>
    </application>

</manifest>

```

Feel free to run the application and see the outcome.

