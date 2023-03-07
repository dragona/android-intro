package mg.x261.mybottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavigationItem navigationItem = NavigationItem.fromMenuItemId(item.getItemId());
                switch (navigationItem) {
                    case HOME:
                        // Handle Home menu item click
                        Toast.makeText(getBaseContext(), "Home", Toast.LENGTH_LONG).show();
                        return true;
                    case DASHBOARD:
                        // Handle Dashboard menu item click
                        Toast.makeText(getBaseContext(), "Dashboard", Toast.LENGTH_LONG).show();
                        return true;
                    case NOTIFICATIONS:
                        // Handle Notifications menu item click
                        Toast.makeText(getBaseContext(), "Notification", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

    public enum NavigationItem {
        HOME(R.id.navigation_home),
        DASHBOARD(R.id.navigation_dashboard),
        NOTIFICATIONS(R.id.navigation_notifications);

        private final int itemId;

        NavigationItem(int itemId) {
            this.itemId = itemId;
        }

        public int getItemId() {
            return itemId;
        }

        public static NavigationItem fromMenuItemId(int itemId) {
            for (NavigationItem item : NavigationItem.values()) {
                if (item.getItemId() == itemId) {
                    return item;
                }
            }
            throw new IllegalArgumentException("Invalid menu item id");
        }
    }


}