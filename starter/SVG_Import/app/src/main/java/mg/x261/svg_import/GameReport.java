package mg.x261.svg_import;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

// TODO: FIX - the text passed through the bundles from GameActivity is not showing. They need to show
// TODO: Add the menu
// TODO: Update history listing when the user plays again and answers the question successfully


public class GameReport extends AppCompatActivity {

    DbHelper dbHelper;
    private List<String> countryMistaken;
    private ArrayList<RowItem> rowItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /**
         * Load the data from the starting activity is any
         */
        Bundle extras = getIntent().getExtras();
        int mistakesCount = extras.getInt("mistake_counts", -1);
        int totalQuestions = extras.getInt("total_questions", -1);
        Log.d("TAG", "mistakesCount : " + mistakesCount + " - totalQuestions: " + totalQuestions);
        if (mistakesCount == -1) {
            Log.d("TAG", "mistakesCount : " + mistakesCount + " - totalQuestions: " + totalQuestions + " INVISIBLE");
            // This Game Report was not started after playing a game
            // -> Hide the section that hold the current game report
            findViewById(R.id.groupGameReport).setVisibility(View.INVISIBLE);
        } else {
            Log.d("TAG", "mistakesCount : " + mistakesCount + " - totalQuestions: " + totalQuestions + " VISIBLE");
            findViewById(R.id.groupGameReport).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tvCountQuestionsPresented)).setText("");
            ((TextView) findViewById(R.id.tvCountCorrectAnswers)).setText("");
            ((TextView) findViewById(R.id.tvCountWrongAnswers)).setText("");

        }


        /**
         * TODO:
         * add a listview and display the data from the database
         */

        World world = new World();
        rowItems = new ArrayList<RowItem>();
        //Read the content of the database
        dbHelper = new DbHelper(this);
        countryMistaken = readingBdContent(dbHelper, world);
        ListView myListView = findViewById(R.id.listViewReport);
        /* At this stage, rowItems should be loaded with the content of the database
         * However, its length is still 0, there is no need for us to show the list*/
        if (rowItems.size() == 0) {
            myListView.setVisibility(View.INVISIBLE);
        } else {
            myListView.setVisibility(View.VISIBLE);
        }
        MyListViewAdapter mAdapter = new MyListViewAdapter(this,
                R.layout.item_history, rowItems);

        myListView.setAdapter(mAdapter);

    }

    // Back press should not directly close the app
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit? \n Press No to restart.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GameReport.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getBaseContext(), ContinentActivity.class));
                        finish();
                    }
                })
                .show();
    }


    private List<String> readingBdContent(DbHelper dbHelper, World world) {
        List<String> mCountryMistaken = new ArrayList<String>();
        Cursor cursor = dbHelper.readData();

        if (cursor.getCount() == 0) {
            //The table is empty
            Log.e(getPackageName(), " Reader: empty");
            mCountryMistaken.add("Empty");
        } else {

            Log.e(getPackageName(), " Reading the content");
            while (cursor.moveToNext()) {
                int continentIndex = Integer.parseInt(cursor.getString(1));
                String flagResourceName = cursor.getString(2);
                RowItem item = new RowItem(world.toCountryName(flagResourceName), flagResourceName);
                rowItems.add(item);

            }

        }
        return mCountryMistaken;
    }


}
