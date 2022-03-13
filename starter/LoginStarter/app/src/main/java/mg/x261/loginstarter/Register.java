package mg.x261.loginstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void btnGoToLogin(View view) {
        // Go to login
        startActivity(new Intent(this, Login.class));
        finish();
    }

    public void btnCreateAccount(View view) {
        // TODO: validate the user input
        // TODO: create a new user account
        Toast.makeText(this, "Create a new user account", Toast.LENGTH_LONG).show();
    }
}