# Android app that retrieves a website and displays its title and links. 

It uses the Jsoup library to connect to the website and parse its HTML content.

The main activity of the app contains a button and a text view. When the button is clicked, it calls the getWebsite() method, which runs a new thread to fetch the website content.

The getWebsite() method first creates a StringBuilder to store the website information. It then uses the Jsoup library to retrieve the website content from a URL. The website content is parsed to get the website title and links. The website title is appended to the StringBuilder, and each link is appended with its URL and text.

If there is an error in fetching the website content, the error message is appended to the StringBuilder.

Finally, the method updates the UI thread with the website information by setting the text of the text view to the content of the StringBuilder.

This app demonstrates how to retrieve and parse website content using the Jsoup library in an Android app.

This code coveres the following Android development concepts:

- Retrieving and parsing website content using the Jsoup library.
```java
Connection.Response response = Jsoup.connect(url_)
                            .ignoreContentType(true)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("https://www.baidu.com")
                            .timeout(12000)
                            .followRedirects(true)
                            .execute(); 

```

- Creating UI elements such as TextView and Button.

```xml
<Button
    android:id="@+id/getBtn"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="50dp"
    android:text="Get website" />

<TextView
    android:id="@+id/result"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_below="@id/getBtn"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="30dp"
    android:text="Result ..."
    android:textSize="17sp" />

```

- Running background tasks using a new thread to prevent blocking the UI thread.

```java
private void getWebsite() {
    new Thread(new Runnable() {
        @Override
        public void run() {
            // background task here
        }
    }).start();
}

```


- Updating the UI thread after completing the background task.

```java
// Update the UI with the result.
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        result.setText(builder.toString());
    }
});

```

- Disabling and enabling the button to prevent multiple clicks.

```java
getBtn.setEnabled(false);
// enable the button
getBtn.setEnabled(true);

```

- Changing the background color of a button programmatically.

```java
getBtn.setBackgroundColor(Color.RED);
// change to the default background color
getBtn.setBackgroundColor(getResources().getColor(R.color.purple_700));

```

- Validating URL strings to check if they start with "http://" or "https://".

```java
private boolean isValidURL(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
        }
 
```

Here is the complete code


```java

package mg.x261.demoparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * The main activity for the DemoParsing app.
 * This code is for an Android app that retrieves a website and displays its title and links.
 * It uses the Jsoup library to connect to the website and parse its HTML content.
 */
public class MainActivity extends AppCompatActivity {
    private Button getBtn;
    private TextView result;

    /**
     * Initializes the activity and sets up the UI elements.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // disable the button
                getBtn.setEnabled(false);
                getBtn.setBackgroundColor(Color.RED);

                getWebsite();

            }
        });
    }

    /**
     * Retrieves a website and displays its title and links in the UI.
     */
    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    String url_ = "https://music.163.com/#/discover/toplist";

                    // Establish a connection to the URL using the Jsoup library, and
                    // set various connection properties such as the user agent, referrer,
                    // and timeout.
                    // The `ignoreContentType` method is set to `true` to allow downloading
                    // non-HTML content such as images or audio files.
                    // Finally, the `execute()` method is called to send the HTTP request and
                    // retrieve the response.
                    Connection.Response response = Jsoup.connect(url_)
                            .ignoreContentType(true)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("https://www.baidu.com")
                            .timeout(12000)
                            .followRedirects(true)
                            .execute();


                    Document doc = response.parse();

                    Log.d("TAG", "run: " + doc);
                    String title = doc.title();
                    Elements links = doc.select("a[href]");

                    builder.append(title).append("\n");
                    int i = 0;
                    for (Element link : links) {
                        String absUrl = link.absUrl("href");
                        String formattedUrl = absUrl;
                        if (!absUrl.startsWith("http")) {
                            formattedUrl = "https://music.163.com" + absUrl;
                        }

                        builder.append("\nURL " + (i + 1) + " : ").append(formattedUrl)
                                .append("\nTitle : ").append(link.text());
                        i++;
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                // Update the UI with the result.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());

                        // enable the button
                        getBtn.setEnabled(true);
                        getBtn.setBackgroundColor(getResources().getColor(R.color.purple_700));
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }


    /**
     * Checks whether the given URL is valid.
     * A valid URL is one that starts with http:// or https://.
     *
     * @param url the URL to check
     * @return true if the URL is valid, false otherwise
     */
    private boolean isValidURL(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }

}


```



```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/getBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="50dp"
        android:text="Load the website - Generate Json" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/getBtn"
        android:layout_marginTop="30dp">

        <TextView
            android:id="@+id/result"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="16dp"
            android:text="-- output --"
            android:textSize="17sp" />
    </ScrollView>

</RelativeLayout>

```

Possible additional improvements that could be implemented as practice include:

- Adding a progress bar to indicate the progress of downloading the website.
- Implementing a feature to save the downloaded website content to local storage.
- Adding a feature to share the website content via social media or email.
- Implementing a feature to search for keywords within the website content.
- Adding a feature to filter the links by their attributes (e.g., target="_blank").
