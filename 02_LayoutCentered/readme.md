
# The form is auto centered (Android Layout)

![Updated version](display/layout.gif)
 

Notice the trick in using a ```ScrollView```.




```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="0dp"
            android:padding="0dp">


            <EditText
                android:id="@+id/et_name"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginEnd="8dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:background="@android:color/white"
                android:ems="10"
                android:hint="To"
                android:inputType="textPostalAddress"
                android:padding="8dp"
                android:textCursorDrawable="@color/colorPrimaryDark"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <EditText
                android:id="@+id/et_address"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginEnd="8dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="16dp"
                android:background="@android:color/white"
                android:hint="Subject"

                android:inputType="text"
                android:padding="8dp"
                android:textCursorDrawable="@color/colorPrimaryDark"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/et_name" />

            <EditText
                android:id="@+id/editText3"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginEnd="8dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="16dp"
                android:background="@android:color/white"

                android:gravity="top"
                android:hint="Message"
                android:inputType="text"
                android:maxLines="10"
                android:minHeight="100dp"
                android:padding="8dp"
                android:textCursorDrawable="@color/colorPrimaryDark"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/et_address" />

            <Button
                android:id="@+id/button"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginEnd="8dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:background="@color/colorPrimary"
                android:text="Send"
                android:textColor="@android:color/white"
                android:textStyle="bold"
                android:typeface="monospace"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.907"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/editText3" />


        </androidx.constraintlayout.widget.ConstraintLayout>

    </ScrollView>


</RelativeLayout>
```
