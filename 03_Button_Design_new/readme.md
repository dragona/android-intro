# Android button design - with selector (2021)

![Buttons](display/button.gif)

If you have checked the [button design](https://github.com/dragona/android-intro/blob/master/03_Button_Design/readme.md) and tried
adding selectors to change color when the button is pressed using

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
if your project uses

```xml
    implementation 'com.google.android.material:material:x.x.x'
```

you need to add

```xml
    app:backgroundTint="@null"
```

the ```Button``` would then be something like this

```xml
    <Button
        android:id="@+id/btn_new"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:background="@drawable/button_selector"
        android:onClick="btlClick"
        android:text="Button 2021"
        android:textColor="@android:color/white"
        android:textStyle="bold"
        app:backgroundTint="@null"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />
```

To recap, there 4 files that require changes to get the selector work in this example:

1- Make the button use the drawable button_selector and add a backgroundTint ```@null```
```xml
        android:background="@drawable/button_selector"
        app:backgroundTint="@null"
```

2- Add the 3 files
```
    - button_selector.xml
    - button_shape.xml
    - button_shape_pressed.xml
```



