package com.wizardofoz.workout.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;


public class LocationsDataSource {
    private SQLiteDatabase database;
    private LocationSQLiteHelper helper;
    private String[] allColumns = {LocationSQLiteHelper.COLUMN_NAME,
            LocationSQLiteHelper.COLUMN_DESCRIPTION,
            LocationSQLiteHelper.COLUMN_IMAGE};

    public LocationsDataSource(Context context){
        helper = new LocationSQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public Location createLocation(String name, String description, byte[] image){
        ContentValues values = new ContentValues();
        values.put(LocationSQLiteHelper.COLUMN_NAME, name);
        values.put(LocationSQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(LocationSQLiteHelper.COLUMN_IMAGE, image);

        long insertId = database.insert(LocationSQLiteHelper.TABLE_LOCATIONS, null, values);
        Cursor cursor = database.query(LocationSQLiteHelper.TABLE_LOCATIONS, allColumns, LocationSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Location location = cursorToLocation(cursor);
        cursor.close();
        return location;
    }

    public ArrayList<Location> getAllLocations(){
        ArrayList<Location> locations = new ArrayList<Location>();

        Cursor cursor = database.query(LocationSQLiteHelper.TABLE_LOCATIONS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Location location = cursorToLocation(cursor);
            locations.add(location);
            cursor.moveToNext();
        }

        cursor.close();
        return locations;
    }

    private Location cursorToLocation(Cursor cursor){
        Location location = new Location();
        location.setName(cursor.getString(0));
        location.setDescription(cursor.getString(1));
        location.setImage(cursor.getBlob(2));

        return location;
    }
}
