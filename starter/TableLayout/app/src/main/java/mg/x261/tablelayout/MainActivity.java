package mg.x261.tablelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Table layout based on https://developer.android.com/guide/topics/ui/layout/grid
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_strech);
        setContentView(R.layout.layout_shrink);
    }
}