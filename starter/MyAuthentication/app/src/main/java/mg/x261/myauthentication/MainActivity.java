package mg.x261.myauthentication;

/**
 * This class represents the main activity of an Android application that handles user authentication by generating a passcode for a student.
 * The application saves the student ID and email in SharedPreferences and sends a POST request to the server to generate a passcode.
 * The server responds with a JSON object containing a generated passcode and its expiration date, which is then displayed to the user.
 * If the server response indicates that the user is already activated, the application starts a new activity called "MainActivityLogged".
 * The code also obtains the Android ID of the device, which is sent along with the POST request parameters.
 */
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.provider.Settings;



/**
 * This class represents the main activity of an Android application that handles user authentication by generating a passcode for a student.
 */
public class MainActivity extends AppCompatActivity {

    private EditText studentIdEditText;
    private EditText emailEditText;
    private Button generatePasscodeButton;

    private ProgressDialog progressDialog;


    /**
     * Initializes the activity and sets up UI elements, event listeners, and SharedPreferences.
     * @param savedInstanceState a Bundle object containing the activity's previously saved state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentIdEditText = findViewById(R.id.student_id_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        generatePasscodeButton = findViewById(R.id.generate_passcode_button);

        // read studentId and email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String studentId = sharedPreferences.getString("student_id", null);
        String email = sharedPreferences.getString("email", null);

        // show a toast message if studentId or email is null
        if (studentId == null || email == null) {
            Toast.makeText(this, "Please enter your student ID and email to generate a passcode.", Toast.LENGTH_SHORT).show();
        } else {
            // set the studentId and email fields to the saved values
            studentIdEditText.setText(studentId);
            emailEditText.setText(email);
        }


        /**
         * Generates a passcode for the given student ID and email by sending a POST request to the server.
         * Parses the server response and displays the generated passcode and its expiration date or starts a new activity if the user is already activated.
         * @param studentId the student ID entered by the user
         * @param email the email address entered by the user
         */
        generatePasscodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = studentIdEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                // Check if the input fields are not empty
                if (studentId.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Student ID and email cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    // Save studentId and email in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("student_id", studentId);
                    editor.putString("email", email);
                    editor.apply();

                    generatePasscode(studentId, email);
                }
            }
        });
    }

    /**
     * Retrieves and returns the Android ID of the device.
     * @return the Android ID of the device
     */
    private void generatePasscode(String studentId, String email) {
        // show progress dialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Generating passcode...");
        progressDialog.show();

        // create POST request
        String url = "https://studio.mg/submission2023/app-access-validation.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            // parse JSON response
                            JSONObject jsonObject = new JSONObject(response);
                            boolean activated = jsonObject.getBoolean("activated");

                            if (activated) {
                                // start new activity
                                Intent intent = new Intent(MainActivity.this, MainActivityLogged.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // get passcode and expiration date
                                String passcode = jsonObject.getString("passcode");
                                String expirationDate = jsonObject.getString("expiration_date");

                                // display passcode and expiration date
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Passcode Generated");
                                builder.setMessage("Your passcode is " + passcode + " and will expire on " + expirationDate);
                                builder.setPositiveButton("OK", null);
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // set POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("student_id", studentId);
                params.put("email", email);
                params.put("device", setAndroidId());
                return params;
            }
        };

        // add request to request queue
        requestQueue.add(stringRequest);
    }


    public String setAndroidId() {
        // Get the Android ID
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        // Log the Android ID
        Log.d("TAG", "Android ID: " + androidId);
        return androidId;
    }
}
