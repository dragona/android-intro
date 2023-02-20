/**
 * TimerService is a service that displays a notification when it is running in the background.
 * The notification can be used to keep the service alive and to show the user that the service is running.
 */
package mg.x261.chronometer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TimerService extends Service {

    /**
     * Called by the system when the service is first created. It is used to initialize any variables or objects needed by the service.
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Called by the system when the service is started. It is used to display a notification to the user indicating that the service is running.
     * @param intent The Intent supplied to startService(Intent), as given. This may be null if the service is being restarted after its process has gone away.
     * @param flags Additional data about this start request. Value is either 0 or START_FLAG_REDELIVERY.
     * @param startId A unique integer representing this specific request to start. Use with stopSelfResult(int).
     * @return The return value indicates what semantics the system should use for the service's current started state. It may be one of the constants associated with the START_CONTINUATION_MASK bits.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Service")
                .setContentText("Service is running...");
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());


        return START_STICKY;
    }

    /**
     * Called by the system when the service is stopped. It is used to clear the notification that was displayed when the service was running.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1); // replace 1 with the ID of your notification
    }

    /**
     * Called by the system when a client explicitly binds to the service by calling bindService(Intent, ServiceConnection, int). It returns an IBinder object that the client can use to communicate with the service.
     * @param intent The Intent that was used to bind to this service, as given to bindService(Intent, ServiceConnection, int).
     * @return Return an IBinder through which clients can call on to the service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
