package mg.x261.weathernotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private WeatherAdapter adapter;
    private BroadcastReceiver weatherUpdateReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<WeatherForecast> newForecasts = intent.getParcelableArrayListExtra(WeatherService.EXTRA_WEATHER_DATA);
                onDataUpdated(newForecasts);
            }
        };
        registerReceiver(weatherUpdateReceiver, new IntentFilter(WeatherService.ACTION_WEATHER_UPDATE));


        // initialize list view and adapter
        listView = findViewById(R.id.list_view);
        adapter = new WeatherAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        // start WeatherService as a foreground service
        Intent serviceIntent = new Intent(this, WeatherService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(weatherUpdateReceiver, new IntentFilter(WeatherService.ACTION_WEATHER_UPDATE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(weatherUpdateReceiver);
        super.onPause();
    }

    private void onDataUpdated(ArrayList<WeatherForecast> forecasts) {
        Log.d("TAG", "Data updated: " + forecasts.size() + " items");
        adapter.setData(forecasts);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(weatherUpdateReceiver);

        // stop WeatherService when activity is destroyed
        Intent serviceIntent = new Intent(this, WeatherService.class);
        stopService(serviceIntent);
    }

}
