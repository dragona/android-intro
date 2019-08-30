![Edit text](./display/edittext.gif)
# Edit text in Android

```java

// Connect the java file to the edit text
EditText myEdittext = (EditText) findViewById(R.id.eTdemo);

// Set text
myEdittext.setText("Demo");

// Get the text from it
String userInput = myEdittext.getText().toString();
```