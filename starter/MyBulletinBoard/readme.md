# Agora Bulletin Board: Android Application Tutorial
In this tutorial, we will create an Android application called "Agora Bulletin Board." The app allows users to view messages on a bulletin board, submit questions, and refresh the data. 
The app will use RecyclerView for displaying messages and interact with a server to fetch and send data.


### Features and Usage Examples

<details>
     <summary>Read more</summary>

1. **Fetching messages:**
    - The app fetches messages from the server and displays them in a list format. Users can view the messages as they scroll through the list.
    - Example: When the app starts, it fetches the latest messages from the server and displays them in the RecyclerView.

2. **Submitting questions:**
    - Users can submit questions to the server by typing their question in the provided input field and tapping the send button.
    - Example: A user types "What is the capital of France?" in the input field and taps the send button. The question is then sent to the server and added to the message list.

3. **Refreshing data:**
    - Users can manually refresh the message list by tapping the refresh button. The app will fetch the latest messages from the server and update the list.
    - Example: After submitting a question, the user taps the refresh button, and the app fetches the updated list of messages, including the user's question and any new messages from other users.

4. **Displaying teacher's message:**
    - When a user submits a question, they receive a response message from the teacher, which is displayed as a toast.
    - Example: After submitting a question, the user receives a toast notification saying, "Teacher's message: Thanks for your question! The capital of France is Paris."

5. **Device ID and time:**
    - The app retrieves the user's device ID and the current time on the device, which are sent as part of the question submission process.
    - Example: When the user submits a question, the app sends their device ID and the current time along with their question to the server.

6. **Permissions and device compatibility:**
    - The app handles runtime permissions and adjusts its behavior based on the user's Android version to ensure compatibility with different devices.
    - Example: If the user's device is running Android Q or later, the app retrieves the device ID using the Settings.Secure method. Otherwise, it requests the READ_PHONE_STATE permission and retrieves the device ID from the TelephonyManager.

</details>

<details>
     <summary>Read more</summary>

### Limitations and Improvements

1. **Real-time updates:**
    - The current implementation requires manual refreshing to fetch new messages. Implementing real-time updates using technologies like WebSockets or Firebase Realtime Database would improve the user experience.

2. **Offline functionality:**
    - The application currently relies on internet connectivity. Implementing local caching and offline support will allow users to view messages and interact with the app even without an internet connection.

3. **User authentication and security:**
    - There is no user authentication or account system in place, which could lead to security and privacy issues. Implementing user authentication using social logins or email and password will improve the security and personalization of the app.

4. **Theming and customization:**
    - The current user interface is quite basic. Improving the visual design, adding theming options, and allowing users to customize their experience would make the app more engaging.

5. **Error handling and user feedback:**
    - Enhance error handling to provide users with more helpful feedback in case of failures. Implement loading indicators to inform users about ongoing network operations.

6. **Accessibility and localization:**
    - Improve accessibility by adding support for screen readers and other assistive technologies. Localize the app to support multiple languages and regions, making it more inclusive.

7. **Performance optimization:**
    - Optimize the app for better performance by reducing memory usage, optimizing images, and minimizing the APK size.

8. **Additional features:**
    - Consider adding more features to the app, such as support for rich media, message reactions, or a search functionality to help users find specific messages more easily.

</details>

1. **Set up a new Android Studio project.**
    - Choose the "Empty Activity" template.
    - Enter the application name, package name, and project location.
    - Choose the minimum SDK version.
    - Click "Finish."

2. **Update the Gradle build files.**
    - Add necessary dependencies in the app-level build.gradle file.
    - 
   <details>
     <summary>Read more</summary>
   
   dependencies {
       implementation 'androidx.appcompat:appcompat:1.6.1'
       implementation 'com.google.android.material:material:1.8.0'
       implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
       implementation 'androidx.recyclerview:recyclerview:1.2.1'
       implementation 'androidx.cardview:cardview:1.0.0'
       implementation 'com.android.volley:volley:1.2.1'
       implementation 'com.google.code.gson:gson:2.10.1'
   
       testImplementation 'junit:junit:4.13.2'
       androidTestImplementation 'androidx.test.ext:junit:1.1.5'
       androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
   }
   
   These dependencies include the necessary libraries for the user interface (AppCompat, Material, ConstraintLayout, RecyclerView, and CardView) as well as libraries for handling API calls (Volley) and JSON parsing (Gson). The test dependencies are also included for running tests in your application.
   
   </details>

3. **Create the Message class.**
    - Define the properties for a message object (e.g., device_id, message, and timer_server, etc).

<details>
  <summary>Read more</summary>

```java
package mg.x261.mybulletinboard;

public class Message {
    private String device_id;
    private String ip_location;
    private String timer_device;
    private String timer_server;
    private String message;

    public String getDeviceId() {
        return device_id;
    }

    public void setDeviceId(String device_id) {
        this.device_id = device_id;
    }

    public String getIpLocation() {
        return ip_location;
    }

    public void setIpLocation(String ip_location) {
        this.ip_location = ip_location;
    }

    public String getTimerDevice() {
        return timer_device;
    }

    public void setTimerDevice(String timer_device) {
        this.timer_device = timer_device;
    }

    public String getTimerServer() {
        return timer_server;
    }

    public void setTimerServer(String timer_server) {
        this.timer_server = timer_server;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```

</details>

4. **Design the main activity layout (activity_main.xml).**
    - Create the main activity layout.

<details>
  <summary>Read more</summary>

</details>

  - Add a TextView for the app title.
  - Add a RecyclerView for displaying messages.
  - Add an ImageButton for refreshing the data.
  - Add a TextInputLayout and TextInputEditText for inputting questions.
  - Add another ImageButton for submitting questions.

<details>
  <summary>Read more</summary>


1. **LinearLayout (Parent)**: It is the parent container with a vertical orientation that holds all the child views.

2. **RelativeLayout**: This layout holds the title of the app and the refresh button, allowing them to be positioned relative to each other.

   - **TextView (tv_bulletin_board_title)**: Displays the title "Agora - Bulletin Board" and is centered horizontally in the RelativeLayout.

   - **ImageButton (btn_refresh_data)**: Displays the refresh icon and is positioned at the right end of the RelativeLayout. It's used to refresh and fetch new data when pressed.

3. **RecyclerView (rv_news_items)**: Displays a list of news items fetched from the server. It uses the `news_item` layout for each item.

4. **LinearLayout**: A horizontal LinearLayout that contains the TextInputLayout for entering questions and the FrameLayout for holding the ProgressBar and ImageButton.

   - **TextInputLayout (til_ask_question)**: Wraps the TextInputEditText and provides a hint "Ask a question" to guide the user. Users can enter their questions here.

      - **TextInputEditText (et_ask_question)**: EditText where users input their questions.

   - **FrameLayout**: Holds the ProgressBar and ImageButton, allowing them to overlap.

      - **ProgressBar (progress_bar)**: Displays a progress spinner while the app is submitting the user's question to the server. It's initially set to "gone" because it should only be visible when the app is waiting for a response from the server.

      - **ImageButton (btn_submit_question)**: Positioned over the ProgressBar and used to submit the user's question when pressed. When the button is pressed, the ProgressBar will become visible, indicating that the app is waiting for a response from the server.


```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="16dp">

        <TextView
            android:id="@+id/tv_bulletin_board_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:text="Agora - Bulletin Board"
            android:textSize="24sp"
            android:textStyle="bold" />

        <ImageButton
            android:id="@+id/btn_refresh_data"
            android:layout_width="25dp"
            android:layout_height="25dp"
            android:scaleType="fitXY"
            android:layout_alignParentEnd="true"
            android:layout_centerVertical="true"
            android:background="@android:color/transparent"
            android:src="@drawable/refresh" />

    </RelativeLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_news_items"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="16dp"
        android:layout_weight="1"
        tools:listitem="@layout/news_item" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/til_ask_question"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:paddingEnd="8dp"
            android:paddingStart="8dp"
            android:layout_weight="1">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/et_ask_question"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Ask a question" />
        </com.google.android.material.textfield.TextInputLayout>

        <FrameLayout
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_marginBottom="8dp">

            <ProgressBar
                android:id="@+id/progress_bar"
                style="?android:attr/progressBarStyle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:visibility="gone" />

            <ImageButton
                android:id="@+id/btn_submit_question"
                android:layout_width="40dp"
                android:layout_height="45dp"
                android:background="@android:color/transparent"
                android:rotation="-10"
                android:scaleType="fitCenter"
                android:src="@drawable/sent" />
        </FrameLayout>

    </LinearLayout>

</LinearLayout>

```

</details>

5. **Design the news item layout (news_item.xml).**
    - Create a CardView layout with the necessary TextViews for the message information.

<details>
  <summary>Read more</summary>


1. **CardView**: A container that provides a material card appearance, with rounded corners and elevation. It serves as the main container for the news item.

2. **LinearLayout (Parent)**: The parent container within the CardView with a vertical orientation, holding all child views.

3. **LinearLayout**: A horizontal LinearLayout that contains the TextView for the news title and the TextView for the timestamp.

   - **TextView (tv_news_title)**: Displays the news title or the device ID in the news item, depending on the data. The text is styled as bold and has a size of 14sp. It has an ellipsize attribute set to "end" and maxLines set to 1, which ensures that the text does not wrap to the next line and gets truncated if it is too long.

   - **TextView (tv_timestamp)**: Displays the timestamp for the news item. The text size is set to 12sp and has a darker gray color.

4. **TextView (tv_news_content)**: Displays the news content or the message associated with the news item. The text is styled with a black color and a size of 16sp. It is placed below the LinearLayout containing the news title and timestamp with a top margin of 8dp.


```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    android:layout_marginBottom="8dp"
    android:layout_marginStart="16dp"
    android:id="@+id/card_view"
    android:layout_marginEnd="16dp"
    app:cardCornerRadius="12dp"
    app:cardElevation="1dp">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        android:paddingTop="8dp"
        android:paddingBottom="8dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/tv_news_title"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="News Title"
                android:textColor="@android:color/darker_gray"
                android:textSize="14sp"
                android:textStyle="bold"
                android:ellipsize="end"
                android:maxLines="1" />

            <TextView
                android:id="@+id/tv_timestamp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Timestamp"
                android:textColor="@android:color/darker_gray"
                android:textSize="12sp" />

        </LinearLayout>

        <TextView
            android:id="@+id/tv_news_content"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="News content goes here..."
            android:textColor="@android:color/black"
            android:textSize="16sp" />

    </LinearLayout>

</androidx.cardview.widget.CardView>

```

</details>


6. **Create the MessageAdapter class.**
    - Extend RecyclerView.Adapter and create a ViewHolder for the message items.
    - Implement the required methods: onCreateViewHolder, onBindViewHolder, and getItemCount.
    - Add a method to change the background color of the CardView based on the device ID.

<details>
  <summary>Read more</summary>

```java

package mg.x261.mybulletinboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MessageAdapter is a custom RecyclerView adapter for displaying messages in a list.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;
    private LayoutInflater layoutInflater;
    private static String userId;

    /**
     * Constructs a new MessageAdapter with a list of messages, a context, and the user's device ID.
     *
     * @param messageList List of messages to display.
     * @param context     Context in which the adapter is used.
     * @param userId      Device ID of the user.
     */
    public MessageAdapter(List<Message> messageList, Context context, String userId) {
        this.messageList = messageList;
        this.layoutInflater = LayoutInflater.from(context);
        this.userId = userId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.news_item, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        holder.bind(currentMessage);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    /**
     * MessageViewHolder is a custom ViewHolder for displaying messages.
     */
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNewsTitle;
        TextView textViewNewsContent;
        TextView textViewTimestamp;
        CardView cardView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNewsTitle = itemView.findViewById(R.id.tv_news_title);
            textViewNewsContent = itemView.findViewById(R.id.tv_news_content);
            textViewTimestamp = itemView.findViewById(R.id.tv_timestamp);
            cardView = itemView.findViewById(R.id.card_view);
        }

        /**
         * Binds a message to the ViewHolder and updates the UI accordingly.
         *
         * @param message The message to bind.
         */
        void bind(Message message) {
            String deviceId = message.getDeviceId();

            // Change the color of the card to light grey if the device ID is the same as the user ID
            if (userId.equals(deviceId)) {
                cardView.setCardBackgroundColor(Color.parseColor("#D3D3D3")); // Light grey color
            } else {
                cardView.setCardBackgroundColor(Color.WHITE); // Reset to white color for other cards
            }

            // Shorten the device ID for display if it is longer than 4 characters
            if (deviceId.length() > 4) {
                String shortenedDeviceId = deviceId.substring(0, 2) + "###" + deviceId.substring(deviceId.length() - 3);
                textViewNewsTitle.setText(shortenedDeviceId);
            } else {
                textViewNewsTitle.setText(deviceId);
            }

            textViewNewsContent.setText(message.getMessage());

            long timestamp = Long.parseLong(message.getTimerServer()) * 1000L; // Assuming the timer server value is in seconds
            Date date = new Date(timestamp);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(date);

            Date currentDate = new Date();
            String formattedCurrentDate = dateFormat.format(currentDate);

            // Format the timestamp differently depending on whether the message was sent today
           if (formattedDate.equals(formattedCurrentDate)) {
              SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
              String formattedTime = timeFormat.format(date);
              textViewTimestamp.setText("Today, " + formattedTime);
           } else {
              SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
              String formattedDateTime = dateTimeFormat.format(date);
              textViewTimestamp.setText(formattedDateTime);
           }

        }
    }
}


```


</details>


7. **Create the MainActivity class.**
    - Initialize and set up the RecyclerView with the MessageAdapter.
    - Set up an onClickListener for the refresh ImageButton.
    - Implement the refreshData() method for fetching new data from the server.
    - Set up an onClickListener for the submit question ImageButton.
    - Implement the submitQuestion() method for sending a question to the server.

<details>
  <summary>Read more</summary>


- `onCreate(Bundle savedInstanceState)`: The main entry point of the activity. It initializes views, sets up the RecyclerView and its adapter, loads data from the server, sets up click listeners for the Send and Refresh buttons, and handles button animations.

- `sendMessage(final String message)`: Sends a message to the server using a POST request. It shows a ProgressBar while sending the request, and parses the response using Gson. It also clears the input field, shows the teacher's message, and reloads data from the server after a short delay.

- `getDeviceTime()`: Returns the device time in seconds.

- `getDeviceId()`: Gets the device ID based on the Android version. For Android Q and above, it uses `Settings.Secure.ANDROID_ID`, otherwise, it uses the `TelephonyManager.getDeviceId()` method.

- `onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)`: Handles the result of a permission request. It checks if the permission is granted or denied, and updates the `deviceId` variable accordingly.

- `loadDataFromServer()`: Loads data from the server using a GET request, parses the response using Gson, and updates the RecyclerView adapter with the new data. It also scrolls to the last item in the RecyclerView.

- `scrollToLastItem()`: Scrolls to the last item in the RecyclerView.

- `hideKeyboard(View view)`: Hides the keyboard after an input field loses focus.

- The `URL_POST_DATA` endpoint (https://studio.mg/api-bulletin-board/post_data.php) returns a JSON object with the following structure: 
```json 
{
    "status": "<string>", // Either "success" or "error"
    "message": "<string>", // A descriptive message about the result of the operation
    "teacher_message": "<string>" // The message from the teacher (only provided if the status is "success")
}
 
```

- The `URL_GET_DATA` endpoint (https://studio.mg/api-bulletin-board/get_data.php) returns a JSON object with the following structure:

```json
{
    "status": "<string>", // Either "success" or "error"
    "message": "<string>", // A descriptive message about the result of the operation
    "messages": [
        {
            "id": "<string>", // The unique ID of the message
            "device_id": "<string>", // The device ID associated with the message
            "ip_location": "<string>", // The IP location of the device
            "timer_device": "<string>", // The device time when the message was created
            "timer_server": "<string>", // The server time when the message was created
            "message": "<string>" // The content of the message
        },
        ...
    ]
}

```

The `status` field indicates whether the operation was successful or not. If successful, the `messages` field contains an array of message objects, each having the fields id, `device_id`, `ip_location`, `timer_device`, `timer_server`, and `message`. The message field provides a descriptive message about the result of the operation, such as an error message in case of failure or a success message.




```java
package mg.x261.mybulletinboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main activity for the MyBulletinBoard app.
 */
public class MainActivity extends AppCompatActivity {

    TextInputEditText etMessage;
    ImageButton btnSend;
    ProgressBar progressBar;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    String deviceId;
    private ImageButton btnRefreshData;

    String URL_POST_DATA = "https://studio.mg/api-bulletin-board/post_data.php";
    String URL_GET_DATA = "https://studio.mg/api-bulletin-board/get_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        // Initialize the message list and message adapter with the device ID
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, this, getDeviceId());

        // Set up the RecyclerView and its adapter
        RecyclerView recyclerView = findViewById(R.id.rv_news_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        etMessage = findViewById(R.id.et_ask_question);
        btnSend = findViewById(R.id.btn_submit_question);

        // Load data from the server when the app starts
        loadDataFromServer();

        // Set up the send button click listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();

                // Check if the message is not empty before sending
                if (!message.isEmpty()) {
                    sendMessage(message);
                    hideKeyboard(v); // Hide the keyboard
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up the refresh button click listener
        btnRefreshData = findViewById(R.id.btn_refresh_data);
        btnRefreshData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromServer();
                // Animate the refresh button
                ObjectAnimator animator = ObjectAnimator.ofFloat(btnRefreshData, "rotation", 0f, 360f);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
            }
        });
    }

    /**
     * Sends a message to the server.
     *
     * @param message The message to be sent.
     */
    private void sendMessage(final String message) {
        Log.d("SendMessage", "Message to be sent: " + message);
        progressBar.setVisibility(View.VISIBLE); // Show the ProgressBar
        btnSend.setEnabled(false); // Disable the button
        btnSend.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SendMessage", "Response from server: " + response);
                        progressBar.setVisibility(View.GONE); // Hide the ProgressBar
                        btnSend.setEnabled(true);
                        btnSend.setVisibility(View.VISIBLE);

                        // Parse the response using Gson
                        Gson gson = new Gson();
                        Type responseType = new TypeToken<PostResponseData>() {
                        }.getType();
                        PostResponseData responseData = gson.fromJson(response, responseType);

                        if (responseData != null && "success".equals(responseData.status)) {

                            etMessage.setText(""); // Clear the TextInputEditText
                            // Show the teacher's message
                            Toast.makeText(MainActivity.this, "Teacher's message: " + responseData.teacher_message, Toast.LENGTH_LONG).show();

                            // Add a delay before reloading the data
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadDataFromServer(); // Update the RecyclerView
                                }
                            }, 2000);
                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + responseData.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE); // Hide the ProgressBar
                        btnSend.setEnabled(true); // Enable the button
                        btnSend.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("device_id", getDeviceId());
                params.put("ip_location", "YOUR_IP_LOCATION");
                params.put("timer_device", String.valueOf(getDeviceTime()));
                params.put("message", message);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public long getDeviceTime() {
        return System.currentTimeMillis() / 1000;
    }

    public String getDeviceId() {
        String deviceId = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                deviceId = telephonyManager.getDeviceId();
            }
        }

        return deviceId;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the device ID
                deviceId = getDeviceId();
                // Use the device ID as needed
            } else {
                // Permission denied, handle the situation
            }
        }
    }


    private void loadDataFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LoadData", "Response from server: " + response);
                        // Parse the response using Gson
                        Gson gson = new Gson();
                        Type responseType = new TypeToken<ResponseData>() {
                        }.getType();
                        ResponseData responseData = gson.fromJson(response, responseType);

                        if (responseData != null && "success".equals(responseData.status)) {
                            // Update the RecyclerView adapter with new data
                            messageList.clear();
                            messageList.addAll(responseData.messages);
                            messageAdapter.notifyDataSetChanged();

                            // Scroll to the last item in the RecyclerView
                            scrollToLastItem();
                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + responseData.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    // Add this new method
    private void scrollToLastItem() {
        RecyclerView recyclerView = findViewById(R.id.rv_news_items);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.scrollToPosition(messageList.size() - 1);
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


```


</details>


8. **Implement data fetching and communication with the server.**
    - Create a method for fetching messages from the server.
    - Create a method for submitting a question to the server.
    - Use appropriate libraries (e.g. Volley) for handling API calls.

<details>
  <summary>Read more</summary>

The code for the data fetching and communication with the server is already available in 7. 
If we break it down, we have:

1. Add the Volley library dependency to your project's build.gradle file.
2. Create a method `loadDataFromServer` for fetching messages from the server:
    - Make a GET request to the `URL_GET_DATA` endpoint using `StringRequest`.
    - Parse the JSON response using Gson or another JSON parsing library.
    - Update the `messageList` and notify the adapter of the changes.
3. Create a method `sendMessage` for submitting a question to the server:
    - Make a POST request to the `URL_POST_DATA` endpoint using `StringRequest`.
    - Pass the required parameters in the `getParams` method.
    - Handle the response and update the UI accordingly.
4. Use the `loadDataFromServer` method to fetch messages when the app starts.
5. Use the `sendMessage` method to submit a question when the user clicks the send button.
6. Handle error cases and provide appropriate feedback to the user.

</details>



9. **Test the application.**
    - Run the application on an emulator or a physical device.
    - Test the functionality of fetching messages, submitting questions, and refreshing the data.

<details>
  <summary>Read more</summary>

To test the application, follow these steps:

1. Set up an Android emulator or connect a physical device to your development environment.
2. Build and run the application on the emulator or the device.
3. Check if the initial data is fetched and displayed correctly in the RecyclerView when the app starts.
4. Test the functionality of submitting a question:
    - Enter a message in the input field.
    - Click the send button and observe if the message is sent to the server.
    - Check if the UI updates correctly after sending the message (e.g. ProgressBar, clearing the input field, etc.).
    - Verify if the sent message appears in the RecyclerView after a short delay.
5. Test the functionality of refreshing the data:
    - Click the refresh button and observe if the data is re-fetched from the server.
    - Check if the RecyclerView updates correctly with the latest data.
6. Test edge cases and error handling, such as empty input, network issues, or server errors. Ensure the app provides appropriate feedback to the user in these cases.

</details>


10. **Optimize and refine the application.**
    - Optimize the user interface and user experience.
    - Improve error handling and edge case scenarios.
    - Test the application on different devices and screen sizes.

<details>
  <summary>Read more</summary>

To optimize and refine the application, consider the following steps:

1. Review and optimize the user interface:
    - Ensure the UI is visually appealing and adheres to Android design guidelines.
    - Make sure the UI elements are aligned, have appropriate margins and paddings, and use consistent styling.
    - Ensure the text is readable and the colors are well-contrasted.
2. Improve the user experience:
    - Add animations and transitions to enhance the overall experience.
    - Implement user-friendly error messages and feedback.
    - Consider adding features like swipe-to-refresh or pull-to-refresh for updating the data.
3. Enhance error handling and edge case scenarios:
    - Test the app in various network conditions, and handle connectivity issues gracefully.
    - Ensure the app handles server errors or unexpected responses correctly, and provides appropriate feedback to the user.
    - Test the app with a large number of messages or very long messages to check for performance issues or layout problems.
4. Test the application on different devices and screen sizes:
    - Use the Android emulator or physical devices with various screen sizes, resolutions, and Android versions to ensure compatibility.
    - Ensure that the layout is responsive and adapts well to different screen sizes and orientations.
    - Test the app on devices with different hardware configurations to ensure smooth performance.

</details>



11. **Prepare the application for release.**
    - Generate a signed APK or App Bundle.
    - Prepare store listing and promotional materials.
    - Publish the application on the Google Play Store.

<details>
  <summary>Read more</summary>

To prepare the application for release, follow these steps:

1. Generate a signed APK or App Bundle:
    - In Android Studio, select "Build" from the menu, and then choose "Generate Signed Bundle / APK."
    - Follow the prompts to create a new signing key or use an existing one.
    - Select the build variant (usually "release") and destination folder for the signed APK or App Bundle.
    - Click "Finish" to generate the signed APK or App Bundle.
2. Prepare store listing and promotional materials:
    - Write a compelling app title and description that clearly explains the app's purpose and features.
    - Create screenshots and promotional images that showcase the app's user interface and key features.
    - Record a video demonstrating the app's functionality, if desired.
    - Prepare a privacy policy, if required.
3. Publish the application on the Google Play Store:
    - Sign up for a Google Play Developer account, if you haven't already, and pay the one-time registration fee.
    - In the Google Play Console, create a new app listing and fill out the required information, such as app title, description, and category.
    - Upload the signed APK or App Bundle, and add the prepared screenshots, images, and video.
    - Provide any additional information, such as content rating and target audience.
    - Review and submit the app for review. Once approved, the app will be published on the Google Play Store.

</details>




