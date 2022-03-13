package mg.x261.loginstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Should not stay here if the user is already logged in
        setContentView(R.layout.activity_login);
    }

    public void btnRegister(View view) {
        // Create a new account
        startActivity(new Intent(this, Register.class));
    }


    public void btnLogin(View view) {
        // Login success
        startActivity(new Intent(this, MainActivity.class));

    }
}