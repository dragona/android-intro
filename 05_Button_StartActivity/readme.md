# An intent for starting another activity


![Part 1](display/part.gif)


```java
package cn.edu.cqu.buttontoast;
/**
 * Introduction to intent and Android Manifest
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connect this java file with the xml layout
        setContentView(R.layout.activity_main);

        // Connect the Button and set the onClick listener
        (findViewById(R.id.btn)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }

}


```
