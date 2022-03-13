package mg.x261.loginstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnLogout(View view) {
        //TODO: Log the user out
        // Go back to Login screen
        startActivity(new Intent(this, Login.class));
    }
}