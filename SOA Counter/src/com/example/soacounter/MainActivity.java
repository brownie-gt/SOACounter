package com.example.soacounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView carView, busView, truckView;
	EditText textAddress;
	int cars = 0, buses = 0, trucks = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textAddress = (EditText) findViewById(R.id.editText1);
		// valor inicial contadores
		carView = (TextView) findViewById(R.id.textView1);
		carView.setText("0");
		busView = (TextView) findViewById(R.id.textView2);
		busView.setText("0");
		truckView = (TextView) findViewById(R.id.textView3);
		truckView.setText("0");
	}

	public void countCar(View view) {
		cars++;
		carView.setText(String.valueOf(cars));
	}

	public void countBus(View view) {
		buses++;
		busView.setText(String.valueOf(buses));
	}

	public void countTruck(View view) {
		trucks++;
		truckView.setText(String.valueOf(trucks));
	}

	public void reset(View view) {
		cars = 0;
		buses = 0;
		trucks = 0;
		carView.setText("0");
		busView.setText("0");
		truckView.setText("0");
		textAddress.setText("");
	}

	public void save(View view) {
		String address = textAddress.getText().toString();
		if (TextUtils.isEmpty(address)) {
			Toast.makeText(this, "Please enter an address", Toast.LENGTH_LONG)
					.show();
			return;
		}
		Log.d("soa", "address: " + address);
		Log.d("soa", carView.getText().toString());
		Log.d("soa", busView.getText().toString());
		Log.d("soa", truckView.getText().toString());

		CountDTO count = new CountDTO();
		CounterDatabaseAdapter db = new CounterDatabaseAdapter(this);
		long inserted = db.insertCount(count);
		Log.d("soa", "Inserted: " + String.valueOf(inserted));
		if (inserted > 0) {
			Toast.makeText(this, "Count saved succesfully!", Toast.LENGTH_LONG)
					.show();
			reset(view);
		} else {
			Toast.makeText(this, "Failed to save count.", Toast.LENGTH_LONG)
					.show();
		}
	}
	
	public void graph(View view){
		Intent myIntent = new Intent(this, ReportActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}
}
