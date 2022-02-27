# Checkbox


```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <CheckBox
        android:id="@+id/checkbox_meat"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="onCheckboxClicked"
        android:text="Meat" />

    <CheckBox
        android:id="@+id/checkbox_cheese"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="onCheckboxClicked"
        android:text="Cheese" />

</LinearLayout>
```


```java
public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_meat:
                if (checked) {
                    Toast.makeText(this, "Meat", Toast.LENGTH_LONG).show();
                    break;

                } else {
                    // Remove the meat
                    Toast.makeText(this, "Remove Meat", Toast.LENGTH_LONG).show();
                    break;

                }


            case R.id.checkbox_cheese:
                if (checked) {
                    Toast.makeText(this, "Cheese", Toast.LENGTH_LONG).show();
                    break;
                }
                // Cheese me
                else {
                    // I'm lactose intolerant
                    Toast.makeText(this, "Remove Cheese", Toast.LENGTH_LONG).show();
                    break;

                }


        }
    }
```