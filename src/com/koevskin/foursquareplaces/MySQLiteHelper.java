package com.koevskin.foursquareplaces;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	  public static final String TABLE_PLACES = "places";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_PLACE = "place";

	  private static final String DATABASE_NAME = "places.db";
	  private static final int DATABASE_VERSION = 1;

	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_PLACES + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_PLACE + " text not null);";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
	    onCreate(db);
	  }

	} 