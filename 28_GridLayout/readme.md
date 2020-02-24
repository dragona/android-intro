# Adding a border to a GridView

![Dragona](gridview.gif)

Create a border layout file and apply it as a background each View or to the GroupView

```xml
<GridLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/tv_title"
        android:layout_centerInParent="true">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_row="0"
            android:layout_column="0"
            android:text="001" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_row="1"
            android:layout_column="0"
            android:text="002" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_row="0"
            android:layout_column="1"
            android:text="003" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_row="1"
            android:layout_column="1"
            android:text="004" />


    </GridLayout>
```