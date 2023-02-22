package mg.x261.activitydemo;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainToast extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toast);

        Toast.makeText(this, "I am a Toast", Toast.LENGTH_SHORT).show();
    }
}
