package com.example.soacounter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class ReportActivity extends Activity {
	CounterDatabaseAdapter dbAdapter;

	public ReportActivity() {
		dbAdapter = new CounterDatabaseAdapter(this);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		Cursor cursor = dbAdapter.getCounts();

		String[] adapterCols = new String[] { "PLACE" };
		int[] adapterRowViews = new int[] { android.R.id.text1 };

		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, adapterCols,
				adapterRowViews, 0);
		sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(sca);

	}
}
