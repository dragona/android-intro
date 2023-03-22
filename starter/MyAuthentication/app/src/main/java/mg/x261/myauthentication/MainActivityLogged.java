package mg.x261.myauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivityLogged extends AppCompatActivity {

    private TextView loggedInTextView;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);
        Button logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(v -> logoutNow());

        loggedInTextView = findViewById(R.id.logged_in_text_view);
        checkIfLoggedIn();
    }

    private void checkIfLoggedIn() {
        SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(this);
        if (!preferencesHelper.isActivated()){
            showNotLoggedInText();
            // move to MainActivityWithoutBinding after 5 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivityLogged.this, MainActivityWithoutBinding.class));
                    finish();
                }
            }, 5000);
        } else {
            showLoggedInText();
        }
    }

    /**
     * Displays the "logged in" status text.
     */
    private void showLoggedInText() {
        loggedInTextView.setText(R.string.logged_in_status);
    }

    /**
     * Displays the "not logged in" status text.
     */
    private void showNotLoggedInText() {
        loggedInTextView.setText(R.string.not_logged_in_text);
    }

    /**
     * Logs out the user by resetting the student ID and email in SharedPreferences and
     * starting the MainActivityWithoutBinding activity.
     */
    private void logoutNow(){
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        sharedPreferencesHelper.resetStudentIdAndEmail();
        startActivity(new Intent(this, MainActivityWithoutBinding.class));
        finish();
    }



}
