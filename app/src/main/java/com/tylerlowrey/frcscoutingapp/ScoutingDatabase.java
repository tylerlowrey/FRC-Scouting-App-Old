package com.tylerlowrey.frcscoutingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ScoutingDatabase extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "scouting.db";
    private static final int VERSION = 2;
    private static final String TAG = "SCOUTING_DATABASE";
    private static ScoutingDatabase instance;

    public ScoutingDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class ScoutingDataTable
    {
        private static final String TABLE_NAME = "scouting";
        private static final String COL_ID = "id";
        private static final String COL_USERNAME = "username";
        private static final String COL_TIMESTAMP = "timestamp";
        private static final String COL_PHOTO_NAME = "photo_filename";
        private static final String COL_FORM_DATA = "form_data";
        private static final String COL_UPLOADED = "uploaded";
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + ScoutingDataTable.TABLE_NAME + "("
                    + ScoutingDataTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ScoutingDataTable.COL_USERNAME +    " text,"
                    + ScoutingDataTable.COL_TIMESTAMP + " INTEGER,"
                    + ScoutingDataTable.COL_PHOTO_NAME +  " text,"
                    + ScoutingDataTable.COL_FORM_DATA +   " text,"
                    + ScoutingDataTable.COL_UPLOADED +  " INTEGER)");
    }

    public static ScoutingDatabase getInstance(Context context)
    {
        if(instance == null)
            instance = new ScoutingDatabase(context);

        return instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + ScoutingDataTable.TABLE_NAME);
        onCreate(db);
    }

    public void saveScoutingData(String username, long timeFromEpoch, String pictureName, String formData)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues queryValues = new ContentValues();
        queryValues.put(ScoutingDataTable.COL_USERNAME, username);
        queryValues.put(ScoutingDataTable.COL_TIMESTAMP, timeFromEpoch);
        queryValues.put(ScoutingDataTable.COL_PHOTO_NAME, pictureName);
        queryValues.put(ScoutingDataTable.COL_FORM_DATA, formData);
        queryValues.put(ScoutingDataTable.COL_UPLOADED, 0);

        db.insert(ScoutingDataTable.TABLE_NAME, null, queryValues);
    }

    public JsonArray getScoutingDataAsJsonArray()
    {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + ScoutingDataTable.TABLE_NAME + " WHERE " + ScoutingDataTable.COL_UPLOADED
                     + " = 0";

        Cursor cursor = db.rawQuery(sql, null);

        JsonArray jsonArray = new JsonArray();

        if(cursor.moveToFirst())
        {
            do
            {
                String username = cursor.getString(1);
                long timestamp = cursor.getLong(2);
                String pictureName = cursor.getString(3);
                String formData = cursor.getString(4);

                JsonObject jsonObject = new JsonObject();
                try
                {
                    jsonObject.addProperty(ScoutingDataTable.COL_USERNAME, username);
                    jsonObject.addProperty(ScoutingDataTable.COL_TIMESTAMP, timestamp);
                    jsonObject.addProperty(ScoutingDataTable.COL_PHOTO_NAME, pictureName);
                    jsonObject.add(ScoutingDataTable.COL_FORM_DATA, (new JsonParser()).parse(formData).getAsJsonObject());

                    jsonArray.add(jsonObject);
                } catch (Exception e) { Log.e(TAG, e.toString()); }

            } while(cursor.moveToNext());
        }

        return  jsonArray;
    }
}
