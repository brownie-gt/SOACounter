package com.example.soacounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CounterDatabaseAdapter {

	CounterHelper helper;

	public CounterDatabaseAdapter(Context context) {
		helper = new CounterHelper(context);
	}

	public long insertCount(Count count) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(CounterHelper.PLACE, count.getPlace());
		content.put(CounterHelper.CAR, count.getCar());
		content.put(CounterHelper.BUS, count.getBus());
		content.put(CounterHelper.TRUCK, count.getTruck());
		return db.insert(CounterHelper.COUNTER_TABLE, null, content);
	}

	static class CounterHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "COUNTER_DB";
		private static final int DATABASE_VERSION = 1;
		private static final String COUNTER_TABLE = "COUNTER_TABLE";

		// CAMPOS
		private static final String UID = "_id";
		private static final String PLACE = "PLACE";
		private static final String CAR = "CAR";
		private static final String BUS = "BUS";
		private static final String TRUCK = "TRUCK";
		private static final String LONG = "LONGITUD";
		private static final String LATITUD = "LATITUD";

		private static final String CREATE_COUNTER_TABLE = "CREATE TABLE "
				+ COUNTER_TABLE + " (" + UID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PLACE
				+ " VARCHAR(255)," + CAR + " INTEGER," + BUS + " INTEGER,"
				+ TRUCK + " INTEGER);";

		private static final String DROP_COUNTER_TABLE = "DROP TABLE IF EXISTS "
				+ COUNTER_TABLE;

		private Context context;

		public CounterHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			Message.message(context, "CounterHelper constructor");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				Message.message(context, "onCreate called");
				db.execSQL(CREATE_COUNTER_TABLE);
				Log.d("SOA", CREATE_COUNTER_TABLE + " CREATED");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				Message.message(context, "onUpgrade called");
				db.execSQL(DROP_COUNTER_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
