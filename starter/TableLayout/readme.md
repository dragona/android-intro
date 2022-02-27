# TableLayout




## stretchColumns

```xml
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:stretchColumns="1"
    tools:context=".MainActivity">

    <TableRow>

        <TextView
            android:layout_column="1"
            android:padding="3dip"
            android:text="Open..." />

        <TextView
            android:gravity="right"
            android:padding="3dip"
            android:text="Ctrl-O" />
    </TableRow>

    <TableRow>

        <TextView
            android:layout_column="1"
            android:padding="3dip"
            android:text="Save..." />

        <TextView
            android:gravity="right"
            android:padding="3dip"
            android:text="Ctrl-S" />
    </TableRow>

    <TableRow>

        <TextView
            android:layout_column="1"
            android:padding="3dip"
            android:text="Save As..." />

        <TextView
            android:gravity="right"
            android:padding="3dip"
            android:text="Ctrl-Shift-S" />
    </TableRow>


</TableLayout>
```

## shrinkColumns

```xml 
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:shrinkColumns="true"
    tools:context=".MainActivity">

    <TableRow>

        <TextView
            android:layout_column="1"
            android:padding="3dip"
            android:text="Open..." />

        <TextView
            android:gravity="end"
            android:padding="3dip"
            android:text="Ctrl-O" />
    </TableRow>

    <TableRow>

        <TextView
            android:layout_column="1"
            android:padding="3dip"
            android:text="Save..." />

        <TextView
            android:gravity="end"
            android:padding="3dip"
            android:text="Ctrl-S" />
    </TableRow>

    <TableRow>
        <TextView
            android:layout_column="1"
            android:padding="3dip"
            android:text="Save As..." />

        <TextView
            android:layout_column="2"
            android:gravity="end"
            android:padding="3dip"
            android:text="Ctrl-Shift-S" />
    </TableRow>
</TableLayout>
```