# Android click listener

Here we present two ways of handling clicks (taps) on buttons.


![Button onclick](display/intent.gif)

- Using the XML layout to set the ```onClick```

- Adding the on ```setOnClickListener``` in the java file.
See the code for the implementation:


### Option one:

```java
// Button One
        (findViewById(R.id.btn)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Your code here
                startActivity(new Intent(getBaseContext(), SecondActivity.class));
            }
        });

```

### Option Two

```java
  // Button two
    public void btnTwo(View view) {
        // Your code here
        startActivity(new Intent(getBaseContext(), SecondActivity.class));

    }
```

for this latter, you need set the button clickable ```android:onClick="btnTwo"```

```xml

    <Button
        android:id="@+id/btn_two"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:onClick="btnTwo"
        android:text="Button two" />

```
