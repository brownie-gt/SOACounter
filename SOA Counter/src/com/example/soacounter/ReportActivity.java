package com.example.soacounter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class ReportActivity extends Activity implements OnItemSelectedListener{

	CounterDatabaseAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("soa", "ReportActivity onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		dbAdapter = new CounterDatabaseAdapter(this);
		Cursor cursor = dbAdapter.getCounts();
		Log.d("soa", "Cursor rows: " + cursor.getCount());
		String[] from = new String[] { "PLACE" };
		int[] to = new int[] { android.R.id.text1 };

		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, from, to, CursorAdapter.NO_SELECTION);
		sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(sca);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
