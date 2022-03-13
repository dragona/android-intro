package mg.x261.loginstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Login extends AppCompatActivity {
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Should not stay here if the user is already logged in
        //Check shared preferences whether a valid token is saved
        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultAccessKey = getResources().getString(R.string.saved_access_token_default);
        String access_token = sharedPref.getString(getString(R.string.user_access_token), defaultAccessKey);
        if (!access_token.equals(defaultAccessKey)) {
            if (true) { // TODO: use the token to log the user (validation needed)
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        }
        Log.d("TAG", access_token + " - " + defaultAccessKey);
        setContentView(R.layout.activity_login);
    }

    public void btnRegister(View view) {
        // Create a new account
        startActivity(new Intent(this, Register.class));
        finish();
    }


    public void btnLogin(View view) {

        /**
         * Let's pretend here that we confirmed  on the server-side that the login and pass
         * valid; and the server returns the following access token that later can be used
         * for faster authentication
         */
        if (true) { // TODO: connect to the server: validate credentials / get token
            String DUMMY_TOKEN = "1234567890";
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_access_token), DUMMY_TOKEN);
            editor.apply();
            // Move the user to the MainActivity screen
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


    }
}