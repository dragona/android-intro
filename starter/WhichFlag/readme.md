# DFlag

This an Android application that makes use of the following


- android:windowSoftInputMode="adjustResize" 
- Card view from material design



## ```android:windowSoftInputMode="adjustResize"```

Set to the activity of the application, it defines how the editText in a form and the soft keyboard should behave in case of possible overlap.

## Card
https://developer.android.com/guide/topics/ui/layout/cardview

```xml 

 <!-- A CardView  -->
    <androidx.cardview.widget.CardView
        android:id="@+id/card_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_margin="16dp"
        android:background="@color/white"
        android:elevation="8dp"
        card_view:cardCornerRadius="8dp">

        <LinearLayout
            android:layout_margin="16dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="Welcome"
                android:textSize="30sp" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="What is your name?"
                android:textSize="16sp" />

            <com.google.android.material.textfield.TextInputLayout
                style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox.Dense"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_margin="16dp"
                android:hint="Username">

                <com.google.android.material.textfield.TextInputEditText
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="8dp"
                    android:maxLength="15"
                    android:singleLine="true" />

            </com.google.android.material.textfield.TextInputLayout>

            <Button
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_margin="16dp"
                android:text="Save"
                android:textSize="16sp"/>

        </LinearLayout>
    </androidx.cardview.widget.CardView>
```


