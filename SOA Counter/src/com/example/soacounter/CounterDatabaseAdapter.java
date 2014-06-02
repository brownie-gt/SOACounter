package com.example.soacounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** Adaptador para conectarse a la DB */
public class CounterDatabaseAdapter {

	CounterHelper helper;
	SQLiteDatabase db;

	public CounterDatabaseAdapter(Context context) {
		helper = new CounterHelper(context);
		db = helper.getWritableDatabase();
	}

	/** Inserta conteo nuevo */
	public long insertCount(CountDTO count) {
		ContentValues content = new ContentValues();
		content.put(CounterHelper.PLACE, count.getPlace());
		content.put(CounterHelper.CAR, count.getCar());
		content.put(CounterHelper.BUS, count.getBus());
		content.put(CounterHelper.TRUCK, count.getTruck());
		content.put(CounterHelper.LONGITUDE, count.getLongitude());
		content.put(CounterHelper.LATITUDE, count.getLatitude());
		return db.insert(CounterHelper.COUNTER_TABLE, null, content);
	}

	/** Obtener conteos */
	public Cursor getCounts() {
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { CounterHelper.UID, CounterHelper.PLACE,
				CounterHelper.CAR, CounterHelper.BUS, CounterHelper.TRUCK,
				CounterHelper.LONGITUDE, CounterHelper.LATITUDE };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = CounterHelper.PLACE + " ASC";

		Cursor c = db.query(CounterHelper.COUNTER_TABLE, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);
		return c;
	}

	// INNER-CLASS para crear y modifacar estructura de BD
	static class CounterHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "COUNTER_DB";
		private static final int DATABASE_VERSION = 3;
		private static final String COUNTER_TABLE = "COUNTER_TABLE";
		// CAMPOS
		public static final String UID = "_id";
		public static final String PLACE = "PLACE";
		public static final String CAR = "CAR";
		public static final String BUS = "BUS";
		public static final String TRUCK = "TRUCK";
		public static final String LONGITUDE = "LONGITUDE";
		public static final String LATITUDE = "LATITUDE";

		private static final String CREATE_COUNTER_TABLE = "CREATE TABLE "
				+ COUNTER_TABLE + " (" + UID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PLACE
				+ " VARCHAR(255)," + CAR + " INTEGER," + BUS + " INTEGER,"
				+ TRUCK + " INTEGER, " + LONGITUDE + " DOUBLE, " + LATITUDE
				+ " DOUBLE);";

		private static final String DROP_COUNTER_TABLE = "DROP TABLE IF EXISTS "
				+ COUNTER_TABLE;

		public CounterHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("soa", "CounterHelper constructor");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				Log.d("soa", "CounterHelper conCreate called");
				db.execSQL(CREATE_COUNTER_TABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				Log.d("soa", "CounterHelper onUpgrade called");
				db.execSQL(DROP_COUNTER_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
