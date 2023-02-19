package mg.x261.activitydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainTable extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));
        tableLayout.setStretchAllColumns(true);

        TableRow row1 = new TableRow(this);

        TextView openText = new TextView(this);
        openText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        openText.setText("Open...");
        openText.setPadding(3, 3, 3, 3);
        openText.setGravity(Gravity.START);

        TextView openShortcut = new TextView(this);
        openShortcut.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        openShortcut.setText("Ctrl-O");
        openShortcut.setPadding(3, 3, 3, 3);
        openShortcut.setGravity(Gravity.RIGHT);

        row1.addView(openText);
        row1.addView(openShortcut);

        TableRow row2 = new TableRow(this);

        TextView saveText = new TextView(this);
        saveText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        saveText.setText("Save...");
        saveText.setPadding(3, 3, 3, 3);
        saveText.setGravity(Gravity.START);

        TextView saveShortcut = new TextView(this);
        saveShortcut.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        saveShortcut.setText("Ctrl-S");
        saveShortcut.setPadding(3, 3, 3, 3);
        saveShortcut.setGravity(Gravity.RIGHT);

        row2.addView(saveText);
        row2.addView(saveShortcut);

        TableRow row3 = new TableRow(this);

        TextView saveAsText = new TextView(this);
        saveAsText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        saveAsText.setText("Save As...");
        saveAsText.setPadding(3, 3, 3, 3);
        saveAsText.setGravity(Gravity.START);

        TextView saveAsShortcut = new TextView(this);
        saveAsShortcut.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        saveAsShortcut.setText("Ctrl-Shift-S");
        saveAsShortcut.setPadding(3, 3, 3, 3);
        saveAsShortcut.setGravity(Gravity.RIGHT);

        row3.addView(saveAsText);
        row3.addView(saveAsShortcut);

        View divider1 = new View(this);
        divider1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, 2));
        divider1.setBackgroundColor(Color.parseColor("#FF909090"));

        TableRow row4 = new TableRow(this);

        TextView x1 = new TextView(this);
        x1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        x1.setText("X");
        x1.setPadding(3, 3, 3, 3);

        TextView importText = new TextView(this);
        importText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        importText.setText("Import...");
        importText.setPadding(3, 3, 3, 3);

        row4.addView(x1);
        row4.addView(importText);

        TableRow row5 = new TableRow(this);

        TextView x2 = new TextView(this);
        x2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        x2.setText("X");
        x2.setPadding(3, 3, 3, 3);

        TextView exportText = new TextView(this);
        exportText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        exportText.setText("Export...");
        exportText.setPadding(3, 3, 3, 3);

        TextView exportShortcut = new TextView(this);
        exportShortcut.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        exportShortcut.setText("Ctrl-E");
        exportShortcut.setPadding(3, 3, 3, 3);
        exportShortcut.setGravity(Gravity.RIGHT);

        row5.addView(x2);
        row5.addView(exportText);
        row5.addView(exportShortcut);

        View divider2 = new View(this);
        divider2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, 2));
        divider2.setBackgroundColor(Color.parseColor("#FF909090"));

        TableRow row6 = new TableRow(this);

        TextView quitText = new TextView(this);
        quitText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        quitText.setText("Quit");
        quitText.setPadding(3, 3, 3, 3);
        quitText.setGravity(Gravity.START);

        row6.addView(quitText);

        tableLayout.addView(row1);
        tableLayout.addView(row2);
        tableLayout.addView(row3);
        tableLayout.addView(divider1);
        tableLayout.addView(row4);
        tableLayout.addView(row5);
        tableLayout.addView(divider2);
        tableLayout.addView(row6);

        setContentView(tableLayout);

    }
}
