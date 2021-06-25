![Intent starts activity](./display/intent.gif)

# Intent used for starting a second activity

Here is the code:

```java
    Intent intent = new Intent(this, Activity_Two.class);
    startActivity(intent);
    
```

I consider that you already have the second activity created ``` Activity_Two.class ``` and you have also
declared that activity in the ```manifest file```

```xml
    <activity
        android:name=".MainActivity"
        android:screenOrientation="portrait">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
    
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
    <activity
        android:name=".Activity_Two"
        android:screenOrientation="portrait" />

```