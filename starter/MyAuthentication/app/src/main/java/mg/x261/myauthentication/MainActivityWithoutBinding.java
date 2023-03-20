package mg.x261.myauthentication;

/***
 * There are three possible return values from the server:
 *
 * If there is a connection error to the database, the server will return an error message in JSON
 * format, with the key "error" and the value "Connection to the database failed!".
 *
 * If the email and device are already registered and activated, the server will return a JSON
 * response with the key "activated" set to "true", and the keys "passcode" and "expiration_date"
 * set to empty strings.
 *
 * If the email and device are not registered or not activated, the server will generate a new
 * passcode, store the student ID, email, passcode, device, IP address, activation status, and
 * expiration date in the database, send a verification link to the student's email, and return
 * a JSON response with the keys "passcode", "expiration_date", and "activated" set to the passcode,
 * expiration date, and false, respectively.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivityWithoutBinding extends AppCompatActivity {

    private EditText studentIdEditText;
    private EditText emailEditText;
    private Button generatePasscodeButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_without_binding);

        studentIdEditText = findViewById(R.id.student_id_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        generatePasscodeButton = findViewById(R.id.generate_passcode_button);
        progressBar = findViewById(R.id.progress_bar);

        initSharedPreferences();

        generatePasscodeButton.setOnClickListener(v -> onClickGeneratePasscode());
    }

    private void initSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String studentId = sharedPreferences.getString("student_id", null);
        String email = sharedPreferences.getString("email", null);

        if (studentId == null || email == null) {
            showToast(R.string.enter_student_id_and_email);
        } else {
            studentIdEditText.setText(studentId);
            emailEditText.setText(email);
        }
    }


    private void onClickGeneratePasscode() {
        String studentId = studentIdEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (studentId.isEmpty() || email.isEmpty()) {
            showToast(R.string.student_id_email_cannot_be_empty);
        } else {
            saveStudentIdAndEmailInPreferences(studentId, email);
            generatePasscode(studentId, email);
        }
    }

    private void saveStudentIdAndEmailInPreferences(String studentId, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("student_id", studentId);
        editor.putString("email", email);
        editor.apply();
    }

    private void generatePasscode(String studentId, String email) {
        showProgressBar(true);

        String url = getString(R.string.server_url);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onPasscodeResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Error: " + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", studentId);
                params.put("email", email);
                params.put("device", getAndroidId());
                return params;
            }
        };

        stringRequest.setShouldCache(false); // Disable caching
        requestQueue.add(stringRequest);
    }



    private void onPasscodeResponse(String response) {
        showProgressBar(false);

        Log.d("PasscodeResponse", response); // log the response from the PHP server

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("error")) { // if there is an error message in the response
                String errorMessage = jsonObject.getString("error");
                if (errorMessage.matches("^-?\\d+$")) { // if the error message is an integer
                    showToast(errorMessage); // show the error message in a toast
                } else { // if the error message is not an integer
                    showToast(errorMessage); // show the error message in a toast
                }
            } else {
                boolean activated = jsonObject.getBoolean("activated");

                if (activated) { // if the account is already activated
                    startMainActivityLogged();
                } else { // if the account is not activated
                    String passcode = jsonObject.getString("passcode");
                    String expirationDate = jsonObject.getString("expiration_date");

                    if (passcode.isEmpty() || expirationDate.isEmpty()) { // if passcode or expiration date is missing
                        showToast(R.string.error_parsing_response); // show a generic error message
                    } else { // if passcode and expiration date are present
                        showPasscodeDialog(passcode, expirationDate); // show the passcode dialog
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToast(R.string.error_parsing_response);
        }
    }

    private void onErrorResponse(VolleyError error) {
        showProgressBar(false);
        showToast(R.string.error_response);
    }

    private void startMainActivityLogged() {
        Intent intent = new Intent(MainActivityWithoutBinding.this, MainActivityLogged.class);
        startActivity(intent);
        finish();
    }

    private void showPasscodeDialog(String passcode, String expirationDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityWithoutBinding.this);
        builder.setTitle(R.string.passcode_generated);
        builder.setMessage(getString(R.string.passcode_expiration) + ": " + passcode + "\n" + expirationDate);
        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    private void showToast(int stringResId) {
        Toast.makeText(MainActivityWithoutBinding.this, stringResId, Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        Toast.makeText(MainActivityWithoutBinding.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public String getAndroidId() {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("TAG", "Android ID: " + androidId);
        return androidId;
    }
}



