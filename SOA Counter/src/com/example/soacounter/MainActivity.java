package com.example.soacounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView carView, busView, truckView;
	int cars = 0, buses = 0, trucks = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
}
