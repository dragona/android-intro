## Change background color - Android layout

![Change background layout color](bg-layout-change.gif)

Have a button to change the background color of a given layout

Steps: 
- register the layout file
- use ```setBackgroundColor()``` to define the bg color
- ```.setBackgroundColor(Color.YELLOW)``` set the color to Yellow


Here is the Java code for the demo shown in the image above

```java
public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.main_layout);
    }

    public void btnYellow(View view) {
        relativeLayout.setBackgroundColor(Color.YELLOW);

    }

    public void btnGreen(View view) {
        relativeLayout.setBackgroundColor(Color.GREEN);
    }

    public void btnRed(View view) {
        relativeLayout.setBackgroundColor(Color.RED);
    }
}


``` 