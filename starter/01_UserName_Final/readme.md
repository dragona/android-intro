## Enhanced User Input Project

Based on the previous demo project, this version includes the following improvements:

- [x] Button click using `setOnClickListener`
- [x] Toast implementation
- [x] Hiding keyboard after button press


![Updated version](display/updated.gif)


--- 
## Snippet

#### Button click or tap
```java
 public class MyActivity extends Activity {
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         setContentView(R.layout.content_layout_id);

         final Button button = findViewById(R.id.button_id);
         button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Code here executes on main thread after user presses button
             }
         });
     }
 }
```

#### Toast

```java

    Context context = getApplicationContext();
    CharSequence text = "Hello toast!";
    int duration = Toast.LENGTH_SHORT;
    
    Toast.makeText(context, text, duration).show();;
    

```

#### Hiding keyboard on button press

```java
    InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

``` 



