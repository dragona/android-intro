![Edit text](./display/edittext.gif)
# Edit text in Android

Define the EditText in your layout file

```xml

    <EditText
        android:id="@+id/eTdemo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:ems="10"
        android:inputType="text">

        <requestFocus />
    </EditText>
```
control it from it based on the ```id``` ```eTdemo``` when using the Java file.

```java

// Connect the java file to the edit text
EditText myEdittext = (EditText) findViewById(R.id.eTdemo);

// Set text
myEdittext.setText("Demo");

// Get the text from it
String userInput = myEdittext.getText().toString();
```