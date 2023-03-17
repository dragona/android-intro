package mg.x261.weathernotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherService extends Service {
    private static final int NOTIFICATION_ID = 123;
    private static final String JSON_URL = "https://studio.mg/api-weather-notification/index.php";
    private static final int POLL_INTERVAL = 60 * 1000; // 1 minute

    // Add constants for the broadcast action and extra data
    public static final String ACTION_WEATHER_UPDATE = "mg.x261.weathernotification.WEATHER_UPDATE";
    public static final String EXTRA_WEATHER_DATA = "weatherData";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("WeatherService", "Service started");

        // Create a notification channel for API level 26 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Weather Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification for the service
        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("Weather Service")
                .setContentText("Fetching weather data...")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();

        // Start the service in the foreground with the notification
        startForeground(NOTIFICATION_ID, notification);

        // Start polling for updates
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                fetchData();
                handler.postDelayed(this, POLL_INTERVAL);
            }
        };
        handler.post(runnable); // Remove the initial delay for the first data fetch

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void fetchData() {
        new AsyncTask<Void, Void, List<WeatherForecast>>() {
            @Override
            protected List<WeatherForecast> doInBackground(Void... voids) {
                // perform network request to retrieve JSON data
                try {
                    URL url = new URL(JSON_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    InputStream stream = connection.getInputStream();

                    // parse JSON response using Gson library
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<WeatherForecast>>() {}.getType();
                    List<WeatherForecast> newForecasts = gson.fromJson(new InputStreamReader(stream), listType);

                    stream.close();
                    connection.disconnect();

                    return newForecasts;
                } catch (IOException e) {
                    Log.e("WeatherService", "Failed to fetch data", e);
                    return null;
                }
            }
            @Override
            protected void onPostExecute(List<WeatherForecast> newForecasts) {
                if (newForecasts != null) {
                    // Call this method when the weather data is updated
                    broadcastWeatherData(newForecasts);
                    Log.d("WeatherService", "Data updated");
                }
            }
        }.execute();
    }
    // Add this method to send a broadcast when the data is updated
    private void broadcastWeatherData(List<WeatherForecast> forecasts) {
        Intent intent = new Intent(ACTION_WEATHER_UPDATE);
        intent.putParcelableArrayListExtra(EXTRA_WEATHER_DATA, new ArrayList<>(forecasts));
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WeatherService", "Service destroyed");
    }
}