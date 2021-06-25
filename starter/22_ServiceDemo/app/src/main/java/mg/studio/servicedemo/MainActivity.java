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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editTextMsgToSend;
    TextView textViewCntReceived, textViewMsgReceived;

    MyMainReceiver myMainReceiver;
    Intent myIntent = null;
    private String TAG_SERVICE = "SERVICE_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMsgToSend = findViewById(R.id.msgtosend);
        textViewCntReceived = findViewById(R.id.cntreceived);
        textViewMsgReceived = findViewById(R.id.msgreceived);
    }


    public void btnStart(View view) {
        Log.d(TAG_SERVICE,"btnStart Pressed");
        startService();
    }

    public void btnStop(View view) {
        Log.d(TAG_SERVICE,"btnStop Pressed");
        stopService();
    }

    /* Sending broadcast */
    public void btnSend(View view) {
        Log.d(TAG_SERVICE,"btnSend message Pressed");
        String msgToService = editTextMsgToSend.getText().toString();
        Intent intent = new Intent();
        intent.setAction(MyService.ACTION_MSG_TO_SERVICE);
        intent.putExtra(MyService.KEY_MSG_TO_SERVICE, msgToService);
        sendBroadcast(intent);
    }




    @Override
    protected void onStart() {

        Log.d(TAG_SERVICE,"onStart");
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        intentFilter.addAction(MyService.ACTION_UPDATE_MSG);
        intentFilter.addAction(MyService.ACTION_UPDATE_USER_SERVICE_DEAD);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG_SERVICE,"onStop");
        unregisterReceiver(myMainReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG_SERVICE,"onDestroy");
        super.onDestroy();
        stopService();
    }


    /* Start - Stop the service */

    private void startService() {
        Log.d(TAG_SERVICE,"startService()");
        myIntent = new Intent(this, MyService.class);
        startService(myIntent);
    }

    private void stopService() {
        Log.d(TAG_SERVICE,"stopService()");
        if (myIntent != null) {
            Log.d(TAG_SERVICE,"stopService() - myIntent != null");
            stopService(myIntent);
            Log.d(TAG_SERVICE,"stopService(myIntent)");
        }
        myIntent = null;
    }


    /* BroadcastReceiver - Receiving */

    private class MyMainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG_SERVICE,"onReceive - intent.getAction(): "+action);
            if (action.equals(MyService.ACTION_UPDATE_CNT)) {
                int int_from_service = intent.getIntExtra(MyService.KEY_INT_FROM_SERVICE, 0);
                textViewCntReceived.setText("Service running for \n " + String.valueOf(int_from_service) + "seconds");
            } else if (action.equals(MyService.ACTION_UPDATE_MSG)) {
                String string_from_service = intent.getStringExtra(MyService.KEY_STRING_FROM_SERVICE);
                textViewMsgReceived.setText(String.valueOf(string_from_service));
            } else if (action.equals(MyService.ACTION_UPDATE_USER_SERVICE_DEAD)) {
                String statusService = intent.getStringExtra(MyService.KEY_STRING_FROM_SERVICE_INFO);
                textViewCntReceived.setText(statusService);
            }

        }
    }
}
