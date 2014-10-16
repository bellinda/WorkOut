package com.wizardofoz.workout.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_LOCATIONS + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text not null, "
            + COLUMN_DESCRIPTION + " text not null, " + COLUMN_IMAGE + " blob not null);";

    public LocationSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
