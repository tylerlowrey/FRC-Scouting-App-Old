package com.tylerlowrey.frcscoutingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoutingDatabase extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "scouting.db";
    private static final int VERSION = 2;

    public ScoutingDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class ScoutingDataTable
    {
        private static final String TABLE_NAME = "scouting";
        private static final String COL_ID = "id";
        private static final String COL_USERNAME = "username";
        private static final String COL_DATETIME = "timestamp";
        private static final String COL_PHOTO_NAME = "photo_filename";
        private static final String COL_FORM_DATA = "form_data";
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + ScoutingDataTable.TABLE_NAME + "("
                    + ScoutingDataTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ScoutingDataTable.COL_USERNAME + " text,"
                    + ScoutingDataTable.COL_DATETIME + " INTEGER,"
                    + ScoutingDataTable.COL_PHOTO_NAME + " text,"
                    + ScoutingDataTable.COL_FORM_DATA + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + ScoutingDataTable.TABLE_NAME);
        onCreate(db);
    }
}
