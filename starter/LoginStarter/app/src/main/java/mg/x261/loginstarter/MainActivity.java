package mg.x261.loginstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnLogout(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String defaultAccessKey = getResources().getString(R.string.saved_access_token_default);
        editor.putString(getString(R.string.user_access_token), defaultAccessKey);
        editor.apply();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}