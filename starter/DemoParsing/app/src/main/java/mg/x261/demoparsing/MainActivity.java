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
