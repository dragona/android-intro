package mg.x261.mytoolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Change the ActionBar title
        getSupportActionBar().setTitle("My Activity Title");

        // Change the Text color from the Toolbar
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));


        // Hide the ActionBar
        //getSupportActionBar().hide();


    }

    // Add a menu item to the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}