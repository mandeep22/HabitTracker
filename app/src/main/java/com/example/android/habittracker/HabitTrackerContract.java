package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JammuMn on 8/18/2016.
 */
public class HabitTrackerContract {
    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "habittracker.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitTrackerContract() {}

    /* Inner class that defines the table contents */
    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "habit";
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_ENTRY_ID +
                        TEXT_TYPE + COMMA_SEP + COLUMN_NAME_TITLE + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class HabitTrackerDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.

        public HabitTrackerDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(HabitEntry.SQL_CREATE_ENTRIES);
            Log.v("Database created", HabitEntry.SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(HabitEntry.SQL_DELETE_ENTRIES);
            Log.v("Database delted", HabitEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public boolean insertHabit (int id, String habit)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(HabitEntry.COLUMN_NAME_ENTRY_ID, id);
            contentValues.put(HabitEntry.COLUMN_NAME_TITLE, habit);
            Log.v("Habit inserted", String.valueOf(db.insert(HabitEntry.TABLE_NAME, null, contentValues)));
            return true;
        }

        public Cursor getHabits() {
            SQLiteDatabase db = this.getReadableDatabase();

            // retrieve all columns and rows from database
            Cursor c = db.rawQuery( "select * from " + HabitEntry.TABLE_NAME, null );

            return c;
        }

        public void deleteHabits ()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(HabitEntry.TABLE_NAME, null, null);
        }

        public boolean updateContact (Integer id, String habit)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(HabitEntry.COLUMN_NAME_TITLE, habit);
            db.update(HabitEntry.TABLE_NAME, contentValues, HabitEntry.COLUMN_NAME_ENTRY_ID +
                    " = ? ", new String[] { Integer.toString(id) } );
            return true;
        }
    }
}
