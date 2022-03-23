package mg.studio.servicedemo;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {

    //from MyService to MainActivity
    final static String KEY_INT_FROM_SERVICE = "KEY_INT_FROM_SERVICE";
    final static String KEY_STRING_FROM_SERVICE = "KEY_STRING_FROM_SERVICE";
    final static String KEY_STRING_FROM_SERVICE_INFO = "KEY_STRING_FROM_SERVICE_INFO";
    final static String ACTION_UPDATE_CNT = "UPDATE_CNT";
    final static String ACTION_UPDATE_MSG = "UPDATE_MSG";
    final static String ACTION_UPDATE_USER_SERVICE_DEAD = "SERVICE_DEAD";

    //from MainActivity to MyService
    final static String KEY_MSG_TO_SERVICE = "KEY_MSG_TO_SERVICE";
    final static String ACTION_MSG_TO_SERVICE = "MSG_TO_SERVICE";


    private static final String TAG_SERVICE = "SERVICE_MyService";

    MyServiceReceiver myServiceReceiver;
    MyServiceThread myServiceThread;
    int counter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG_SERVICE, "onCreate()");
        myServiceReceiver = new MyServiceReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG_SERVICE, "onStartCommand()");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MSG_TO_SERVICE);
        registerReceiver(myServiceReceiver, intentFilter);

        myServiceThread = new MyServiceThread();
        myServiceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG_SERVICE, "onDestroy");
        myServiceThread.setRunning(false);
        unregisterReceiver(myServiceReceiver);
        super.onDestroy();
    }

    public class MyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(ACTION_MSG_TO_SERVICE)) {
                String msg = intent.getStringExtra(KEY_MSG_TO_SERVICE);

                msg = new StringBuilder(msg).reverse().toString();

                //send back to MainActivity
                Intent i = new Intent();
                i.setAction(ACTION_UPDATE_MSG);
                i.putExtra(KEY_STRING_FROM_SERVICE, msg);
                sendBroadcast(i);
            }
        }
    }


    private class MyServiceThread extends Thread {
        private boolean running;
        public void setRunning(boolean running) {
            this.running = running;
        }
        @Override
        public void run() {
            counter = 1;
            running = true;
            while (running) {
                try {
                    Thread.sleep(1000);

                    Intent intent = new Intent();
                    intent.setAction(ACTION_UPDATE_CNT);
                    intent.putExtra(KEY_INT_FROM_SERVICE, counter);
                    sendBroadcast(intent);
                    Log.d(TAG_SERVICE, "MyServiceThread(): " + counter);
                    counter++;
                    /*
                     *running is false when Destroy has been called
                     */
                    if (!running) updateDisplayEndService();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Inform the user that the Service is not working
    private void updateDisplayEndService() {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_USER_SERVICE_DEAD);
        intent.putExtra(KEY_STRING_FROM_SERVICE_INFO, "Service is Dead");
        sendBroadcast(intent);
    }

}
