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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class MainActivityWithoutBinding extends AppCompatActivity {

    private EditText studentIdEditText;
    private EditText emailEditText;
    private Button generatePasscodeButton, activateAccount;
    private ProgressBar progressBar;
    private TextView textViewMessageNotification;
    private LinearLayout layoutNotification;

    String studentId = null;
    String email = null;
    SharedPreferencesHelper sharedPreferencesHelper;
    private Dialog mDialog;

    /**
     * MainActivityWithoutBinding is responsible for activating an account by generating a passcode and sending it to the user's email.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_without_binding);

        // Initialize view elements
        textViewMessageNotification = findViewById(R.id.tvMessageNotification);
        layoutNotification = findViewById(R.id.layout_notification);
        studentIdEditText = findViewById(R.id.student_id_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        progressBar = findViewById(R.id.progress_bar);
        generatePasscodeButton = findViewById(R.id.generate_passcode_button);
        activateAccount = findViewById(R.id.activation_button);

        // Set OnClickListener for generate passcode button
        generatePasscodeButton.setOnClickListener(v -> {
            hideKeyboard(v);
            onClickGeneratePasscode();
        });

        // Set OnFocusChangeListener for student ID and email EditTexts to hide keyboard when not in focus
        View.OnFocusChangeListener focusChangeListener = (v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        };
        studentIdEditText.setOnFocusChangeListener(focusChangeListener);
        emailEditText.setOnFocusChangeListener(focusChangeListener);

        // Set OnClickListener for activate account button
        activateAccount.setOnClickListener(v -> ToastHelper.showToast(this, "TODO"));

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        // Check if student ID and email exist in SharedPreferences
        if (sharedPreferencesHelper.isStudentIdAndEmailExist()) {
            // Set EditTexts with values from SharedPreferences
            sharedPreferencesHelper.initEditTextsFromSharedPreferences(studentIdEditText, emailEditText);
            // Hide form and get student ID and email values
            showProgressBar();
            studentId = sharedPreferencesHelper.getStudentId();
            email = sharedPreferencesHelper.getEmail();
            // Generate passcode if both student ID and email values are not null
            if (studentId != null && email != null) {
                validateAccount(studentId, email);
            } else {
                showForm();
            }
        } else {
            // Show form if student ID and email do not exist in SharedPreferences
            showForm();
        }
    }

    /**
     * Hides the soft keyboard.
     *
     * @param view The current view that has the focus.
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        // Check if inputMethodManager and view are not null
        if (inputMethodManager != null && view != null) {
            IBinder windowToken = view.getWindowToken();
            // Check if the window token is not null
            if (windowToken != null) {
                // Hide the soft keyboard from the window
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    /**
     * Show the form where the user can enter their student ID and email.
     */
    private void showForm() {
        studentIdEditText.setVisibility(View.VISIBLE);
        emailEditText.setVisibility(View.VISIBLE);
        generatePasscodeButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        layoutNotification.setVisibility(View.GONE);
    }

    /**
     * Show the progress bar while the app is loading.
     */
    private void showProgressBar() {
        studentIdEditText.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);
        generatePasscodeButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        layoutNotification.setVisibility(View.GONE);
    }

    /**
     * Show a notification with a message to the user.
     *
     * @param message the message to display in the notification
     */
    private void showNotification(String message) {
        studentIdEditText.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);
        generatePasscodeButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        layoutNotification.setVisibility(View.VISIBLE);
        textViewMessageNotification.setText(message);
    }

    /**
     * This method is called when the "Generate Passcode" button is clicked.
     * It gets the student ID and email from the EditText fields and checks if they are empty.
     * If they are not empty, it calls the validateAccount method.
     * If they are empty, it shows a toast message to inform the user that the fields cannot be empty.
     */
    private void onClickGeneratePasscode() {
        studentId = studentIdEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();

        if (studentId.isEmpty() || email.isEmpty()) {
            ToastHelper.showToast(this, R.string.student_id_email_cannot_be_empty);
        } else {
            validateAccount(studentId, email);
        }
    }

    /**
     * Validates the student account by sending a POST request to the server with the provided student ID, email,
     * and device ID. Upon receiving a response from the server, the onPasscodeResponse method is called.
     *
     * @param studentId the student ID to validate
     * @param email     the email to validate
     */
    private void validateAccount(String studentId, String email) {
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


    /**
     * Decodes a URL string that may contain escape sequences.
     *
     * @param url the URL string to be decoded
     * @return the decoded URL string
     */
    public static String decodeURL(String url) {
        String unescapedUrl = url.replaceAll("\\\\/", "/").replaceAll("\\\\\\\\", "\\\\");
        try {
            return URLDecoder.decode(unescapedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("Decode URL : ", "Error : " + e);
            return "";
        }
    }


    /**
     * Handles the response from the server after validating the account.
     * @param response the response from the server
     */
    private void onPasscodeResponse(String response) {
        showProgressBar(false);

        Log.d("PasscodeResponse", decodeURL(response)); // log the response from the PHP server
        Log.d("buildVerificationLink", buildVerificationLink(studentId, "passcode", "deviceId", email)); // log the response from the PHP server

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("error")) { // if there is an error message in the response
                String errorMessage = jsonObject.getString("error");
                showNotification(errorMessage);
            } else {
                boolean activated = jsonObject.getBoolean("activated");

                if (activated) { // if the account is already activated
                    startMainActivityLogged();
                } else { // if the account is not activated
                    String passcode = jsonObject.getString("passcode");
                    String expirationDate = jsonObject.getString("expiration_date");
                    // TODO: this verification link should not be from the server
                    String verificationLink = jsonObject.getString("verification_link");

                    if (passcode.isEmpty() || expirationDate.isEmpty()) { // if passcode or expiration date is missing
                        ToastHelper.showToast(this, R.string.error_parsing_response); // show a generic error message
                    } else { // if passcode and expiration date are present
                        // show the passcode dialog
                        showDialogForAccountValidation(passcode, verificationLink);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ToastHelper.showToast(this, R.string.error_parsing_response);
        }
    }
    /**
     * Shows a dialog to enter the passcode for account validation.
     *
     * @param passcode the generated passcode received from the server
     * @param verificationLink the link to be sent to the student's email for account activation
     */
    private void showDialogForAccountValidation(String passcode, String verificationLink) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityWithoutBinding.this);
        builder.setTitle(R.string.enter_passcode_title);

        View viewInflated = LayoutInflater.from(MainActivityWithoutBinding.this)
                .inflate(R.layout.dialog_enter_passcode, (ViewGroup) findViewById(android.R.id.content), false);

        // Set up the input
        final EditText input = viewInflated.findViewById(R.id.passcode_input);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(viewInflated);

        viewInflated.findViewById(R.id.validate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcodeEntered = input.getText().toString();
                if (passcodeEntered.equals(passcode)) {
                    // send verification link to student's email
                    studentId = studentIdEditText.getText().toString().trim();
                    email = emailEditText.getText().toString().trim();
                    sendVerificationLink(studentId, email, verificationLink);

                    if (sharedPreferencesHelper.isStudentIdAndEmailExist()) {
                        // Set EditTexts with values from SharedPreferences
                        sharedPreferencesHelper.initEditTextsFromSharedPreferences(studentIdEditText, emailEditText);
                    }

                } else {
                    ToastHelper.showToast(MainActivityWithoutBinding.this, R.string.passcode_incorrect);
                }
                mDialog.dismiss();
                showForm();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        mDialog = builder.show();
    }
    /**
     * Sends a verification link to the student's email and activates their account if successful.
     * @param studentId the student's ID
     * @param email the student's email
     * @param verificationLink the verification link to send to the student's email
     */
    private void sendVerificationLink(String studentId, String email, String verificationLink) {

        // Instantiate a new RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a new StringRequest to send the verification link
        StringRequest stringRequest = new StringRequest(Request.Method.GET, verificationLink,
                // Define what to do when the response is received
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            // Parse the response JSON
                            JSONObject jsonObject = new JSONObject(response);
                            boolean activated = jsonObject.getBoolean("activated");
                            String activationError = jsonObject.getString("error");
                            Log.d("Activation", "Activation : " + activationError);
                            if (activated) {
                                // Account activated successfully
                                startMainActivityLogged();
                            } else {
                                // Account activation failed
                                ToastHelper.showToast(MainActivityWithoutBinding.this, R.string.account_activation_failed);
                            }
                        } catch (JSONException e) {
                            Log.d("Parsing", "Error : " + e);
                            ToastHelper.showToast(MainActivityWithoutBinding.this, R.string.error_parsing_response);
                        }
                    }
                },
                // Define what to do when there is an error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastHelper.showToast(MainActivityWithoutBinding.this, R.string.error_response);
                    }
                });

        // Add the StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }

    /**
     * Builds the verification link using the given parameters.
     *
     * @param studentId The student ID.
     * @param passcode  The passcode.
     * @param deviceId  The device ID.
     * @param email     The email address.
     * @return The verification link.
     */
    private String buildVerificationLink(String studentId, String passcode, String deviceId, String email) {
        return "https://studio.mg/submission2023/verify.php?" +
                "student_id=" + studentId +
                "&passcode=" + passcode +
                "&device=" + deviceId +
                "&email=" + email;
    }

    /**
     * Handles error response from Volley request.
     *
     * @param error The error response.
     */
    private void onErrorResponse(VolleyError error) {
        showProgressBar(false);
        ToastHelper.showToast(this, R.string.error_response);
    }

    /**
     * Starts the MainActivityLogged and saves the student ID and email in SharedPreferences.
     */
    private void startMainActivityLogged() {
        sharedPreferencesHelper.setStudentIdAndEmail(studentId, email);
        sharedPreferencesHelper.setActivated();
        Intent intent = new Intent(MainActivityWithoutBinding.this, MainActivityLogged.class);
        startActivity(intent);
        finish();
    }

    /**
     * Shows a dialog with the generated passcode and its expiration date.
     *
     * @param passcode the generated passcode
     * @param expirationDate the expiration date of the passcode
     */
    private void showPasscodeDialog(String passcode, String expirationDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityWithoutBinding.this);
        builder.setTitle(R.string.passcode_generated);
        builder.setMessage(getString(R.string.passcode_expiration) + ": " + passcode + "\n" + expirationDate);
        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    /**
     * Shows or hides the progress bar depending on the value of the 'show' parameter.
     *
     * @param show a boolean indicating whether the progress bar should be shown or hidden
     */
    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Retrieves the unique identifier for this Android device.
     *
     * @return a String containing the Android ID
     */
    public String getAndroidId() {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("TAG", "Android ID: " + androidId);
        return androidId;
    }

}



