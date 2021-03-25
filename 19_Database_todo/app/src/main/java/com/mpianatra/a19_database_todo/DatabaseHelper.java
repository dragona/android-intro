package com.mpianatra.a19_database_todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Student.db";
    public static String TABLE_NAME = "t_student";

    public String COL_2 = "name";
    public String COL_3 = "surname";
    public String COL_4 = "marks";


    //Simplified constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create the database
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "id integer primary key autoincrement," +
                "name text," +
                "surname text," +
                "marks integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name, String surname, String marks) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);

        //Table name, null and the content values are needed as param

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            //Insert has failed
            return false;
        } else {
            //Successful insertion
            return true;
        }
    }

    public Cursor readData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }
}