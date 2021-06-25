# Android button design

It comes handy to have sample button designs. Here are 6 core designs which I find foundational:
1. [Button simple: simple standard design](#button-simple-simple-standard-design)
2. [Button Two: with specific colors](#button-two-with-specific-colors)
3. [Button Three: color and rounded corners](#button-three-color-and-rounded-corners)
4. [Button four: clickable from the XML file](#button-four-clickable-from-the-xml-ile)
5. [button five: explicit onclick listener in the java file](#button-five-explicit-onclick-listener-in-the-java-file)
6. [Button six: adding selectors to change color when the button is pressed](#button-six-adding-selectors-to-change-color-when-the-button-is-pressed)

![Buttons](display/buttons.gif)


# Button 1: simple standard design


```xml
    <Button
        android:id="@+id/btn_simple"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button simple"
        android:layout_margin="8dp"/>
```

# Button 2: with specific color

```xml
    <Button
        android:id="@+id/btn_two"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button two"
        android:layout_margin="8dp"
        android:elevation="24dp"
        android:background="@color/colorAccent"
        android:textColor="@android:color/white"
        android:textStyle="bold" />

```

# Button 3: color and rounded corners

Create a ```button_shape.xml``` to specify the shape and color of the button. Save it to the project's drawable folder.

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/colorAccent" />
    <corners android:radius="8dp" />
</shape>
```

in the ```Button```, specify the use of a ```android:background="@drawable/button_shape"``` 

```xml
    <Button
        android:id="@+id/btn_three"
        android:layout_below="@+id/btn_two"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button Three"
        android:layout_margin="8dp"
        android:background="@drawable/button_shape"
        android:textColor="@android:color/white"
        android:textStyle="bold" />
```

# Button 4: clickable from the xml file

You can set the conclick listener of button directly in the Java file, but one easy way is to set the ```Button``` clickable from the
 xml file by adding ```android:onClick="bntXml"```. If you are using Android Studio, just over that line and you 
 will be prompted whether you want to create the public function that connects to that button. 

```xml
    <Button
        android:id="@+id/btn_four"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button Four - click xml"
        android:layout_margin="8dp"
        android:background="@drawable/button_shape"
        android:textColor="@android:color/white"
        android:textStyle="bold"
        android:onClick="bntXml"/>
```

the Java file would be added with something that looks like this

```java
    public void bntXml(View view) {
        // Add your code here
    }
```

Just for illustration, we will add a toast so when the button is clicked, the toast should show. Thus, the above java code becomes

```java
public void bntXml(View view) {
        Toast.makeText(this, ((Button)findViewById(R.id.btn_four)).getText(),Toast.LENGTH_LONG).show();
    }

```

# button 5: explicit onclick listener in the java file

```xml
    <Button
        android:id="@+id/btn_five"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button Five - explicit listener"
        android:layout_margin="8dp"
        android:background="@drawable/button_shape"
        android:textColor="@android:color/white"
        android:textStyle="bold"/>
```
here is an example of the java class activity

```java

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button Five
        findViewById(R.id.btn_five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), ((Button)findViewById(R.id.btn_five)).getText(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

```

# Button 6: adding selectors to change the button's color when it is pressed

Just like for the ```Button 5```, create the xml file which you can name ```button_shape.xml``` 
to define the default shape and the color of the button. Save it to your drawable folder.

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/colorAccent" />
    <corners android:radius="8dp" />
</shape>
```

Now, create another file that defines the color and shape of the button when it is pressed. You can name the button 
```button_shape_pressed.xml``` and save it to the drawable folder, just like the first file.


```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/colorPrimaryDark" />
    <corners android:radius="8dp" />
</shape>
```

Next, create a third file, which is a selector. The selector specifies which of the files ```button_shape``` and ```button_shape_pressed``` to use depending the the button state.
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!--When the button is pressed-->
    <item android:drawable="@drawable/button_shape_pressed"
        android:state_pressed="true" />
    <!--Default-->        
    <item android:drawable="@drawable/button_shape" />
</selector>

```

Time to create you button in the layout xml file and to use the ```button_selector.xml ``` as the button's background.
```android:background="@drawable/button_selector"```

in real, the xml code for the button should look like this:

```xml
 <Button
        android:id="@+id/btn_six"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button Six - button selector"
        android:layout_margin="8dp"
        android:background="@drawable/button_selector"
        android:textColor="@android:color/white"
        android:textStyle="bold"/>
```