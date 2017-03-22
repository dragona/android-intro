package cn.edu.cqu.getcolor;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView display_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display_color = (TextView) findViewById(R.id.tv);

        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                int x = (int) event.getX();
                int y = (int) event.getY();
                int pixel = bitmap.getPixel(x, y);

                //then do what you want with the pixel data, e.g
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                Log.e("TAG", "R: " + redValue + " G: " + greenValue + " B: " + blueValue);
                display_color.setText("R: " + redValue + " G: " + greenValue + " B: " + blueValue);
                return false;
            }
        });

    }


}
