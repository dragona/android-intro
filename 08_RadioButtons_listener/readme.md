# Radio buttons

```xml
    <RadioGroup
        android:id="@+id/radioGroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_marginTop="139dp">

        <RadioButton
            android:id="@+id/radio0"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:checked="true"
            android:text="@string/txt_radio0" />

        <RadioButton
            android:id="@+id/radio1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/txt_radio1" />

        <RadioButton
            android:id="@+id/radio2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/txt_radio2" />
    </RadioGroup>
```

```java
public class MainActivity extends Activity implements OnCheckedChangeListener {
    RadioGroup radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Connect the radioGroup
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        radiogroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        if (arg1 == R.id.radio0) {
            Toast.makeText(getBaseContext(), "I love you ", Toast.LENGTH_SHORT).show();
        }
        if (arg1 == R.id.radio1) {
            Toast.makeText(getBaseContext(), "I like you", Toast.LENGTH_SHORT).show();
        }
        if (arg1 == R.id.radio2) {
            Toast.makeText(getBaseContext(), "Hum...", Toast.LENGTH_SHORT).show();
        }

    }
}

```