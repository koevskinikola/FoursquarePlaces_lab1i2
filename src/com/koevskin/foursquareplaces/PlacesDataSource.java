package com.koevskin.foursquareplaces;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PlacesDataSource {

	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_PLACE };

	  public PlacesDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Place createPlace(String place) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_PLACE, place);
	    long insertId = database.insert(MySQLiteHelper.TABLE_PLACES, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PLACES,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Place newPlace = cursorToPlace(cursor);
	    cursor.close();
	    return newPlace;
	  }

	  public void deletePlace(Place comment) {
	    long id = comment.getId();
	    System.out.println("Place deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_PLACES, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public ArrayList<String> getAllPlaces() {
		  ArrayList<String> places = new ArrayList<String>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PLACES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Place place = cursorToPlace(cursor);
	    	places.add(place.getPlace());
	      cursor.moveToNext();
	    }

	    cursor.close();
	    return places;
	  }

	  private Place cursorToPlace(Cursor cursor) {
		  Place place = new Place();
		  place.setId(cursor.getLong(0));
		  place.setPlace(cursor.getString(1));
	    return place;
	  }
	} 