package mg.x261.mybulletinboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    TextInputEditText etMessage;
    ImageButton btnSend;
    ProgressBar progressBar;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    String deviceId;

    String URL_POST_DATA = "https://studio.mg/api-bulletin-board/post_data.php";
    String URL_GET_DATA = "https://studio.mg/api-bulletin-board/get_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        // Initialize the messageList and messageAdapter before making the request
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, this);


        // Initialize your RecyclerView and its adapter
        RecyclerView recyclerView = findViewById(R.id.rv_news_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);


        etMessage = findViewById(R.id.et_ask_question);
        btnSend = findViewById(R.id.btn_submit_question);


        loadDataFromServer(); // Load data when the app starts


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();

                if (!message.isEmpty()) {
                    sendMessage(message);
                    hideKeyboard(v); // Hide the keyboard

                } else {
                    Toast.makeText(MainActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
