package mg.x261.checkbox;


/**
 * Source : https://developer.android.com/guide/topics/ui/controls/checkbox
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_meat:
                if (checked) {
                    Toast.makeText(this, "Meat", Toast.LENGTH_LONG).show();
                    break;

                } else {
                    // Remove the meat
                    Toast.makeText(this, "Remove Meat", Toast.LENGTH_LONG).show();
                    break;

                }

            case R.id.checkbox_cheese:
                if (checked) {
                    Toast.makeText(this, "Cheese", Toast.LENGTH_LONG).show();
                    break;
                }
                // Cheese me
                else {
                    // I'm lactose intolerant
                    Toast.makeText(this, "Remove Cheese", Toast.LENGTH_LONG).show();
                    break;

                }


        }
    }
}