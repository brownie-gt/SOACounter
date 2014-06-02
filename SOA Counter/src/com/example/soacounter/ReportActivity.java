package com.example.soacounter;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.soacounter.CounterDatabaseAdapter.CounterHelper;

public class ReportActivity extends Activity implements OnItemSelectedListener {

	CounterDatabaseAdapter dbAdapter;
	CountDTO count = new CountDTO();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("soa", "ReportActivity onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		dbAdapter = new CounterDatabaseAdapter(this);
		Cursor cursor = dbAdapter.getCounts();
		Log.d("soa", "Cursor rows: " + cursor.getCount());

		// --- Sets spinner with data stored in DB
		String[] adapterCols = new String[] { CounterHelper.UID,
				CounterHelper.PLACE, CounterHelper.CAR, CounterHelper.BUS,
				CounterHelper.TRUCK, CounterHelper.LONGITUDE,
				CounterHelper.LATITUDE };
		int[] adapterRowViews = new int[] { android.R.id.text1 };

		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, adapterCols,
				adapterRowViews, CursorAdapter.NO_SELECTION);
		sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(sca);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("soa", "onItemSelected");
		refreshCountAtPosition(parent, position);
		updateViews();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	public void refreshCountAtPosition(AdapterView<?> parent, int position) {
		Log.d("soa", "refreshCountAtPosition");
		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		count = new CountDTO();
		count.setId(cursor.getInt(cursor.getColumnIndex(CounterHelper.UID)));
		count.setPlace(cursor.getString(cursor
				.getColumnIndex(CounterHelper.PLACE)));
		count.setCar(cursor.getInt(cursor.getColumnIndex(CounterHelper.CAR)));
		count.setBus(cursor.getInt(cursor.getColumnIndex(CounterHelper.BUS)));
		count.setTruck(cursor.getInt(cursor.getColumnIndex(CounterHelper.TRUCK)));
		count.setLongitude(cursor.getDouble(cursor
				.getColumnIndex(CounterHelper.LONGITUDE)));
		count.setLatitude(cursor.getDouble(cursor
				.getColumnIndex(CounterHelper.LATITUDE)));
		Log.d("soa", "Item selected ID:" + count.getId());
		Log.d("soa", "Item selected address:" + count.getPlace());
	}

	public void updateViews() {
		TextView car, bus, truck, lon, lat;

		car = (TextView) findViewById(R.id.txtCar);
		bus = (TextView) findViewById(R.id.txtBus);
		truck = (TextView) findViewById(R.id.txtTrucks);
		lon = (TextView) findViewById(R.id.txtLongitude);
		lat = (TextView) findViewById(R.id.txtLatitude);

		car.setText(String.valueOf(count.getCar()));
		bus.setText(String.valueOf(count.getBus()));
		truck.setText(String.valueOf(count.getTruck()));
		lon.setText(String.valueOf(count.getLongitude()));
		lat.setText(String.valueOf(count.getLatitude()));
	}

	private String[] TCar = new String[] { "Car", "Bus", "Truck" };

	public void showChart(View view) {
		openChart();
	}

	private void openChart() {
		int[] cars = { 0, 1, 2 };
		int[] quantity = { count.getCar(), count.getBus(), count.getTruck() };

		// Creating an XYSeries for Income
		XYSeries incomeSeries = new XYSeries("Quantity");
		// Adding data to Income Series
		for (int i = 0; i < cars.length; i++) {
			incomeSeries.add(i, quantity[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(incomeSeries);
		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.rgb(130, 130, 230));
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(0);
		incomeRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Traffic Counter Chart"); // Place
		// "Nombre del Lugar"...
		multiRenderer.setXTitle("Vehicles");
		multiRenderer.setYTitle("Quantity");
		
		multiRenderer.setZoomButtonsVisible(true);
		multiRenderer.setLabelsTextSize(20);
		multiRenderer.setLegendTextSize(20);
		multiRenderer.setXAxisMax(5);
		multiRenderer.setYAxisMax(10);
		multiRenderer.setBarSpacing(1);
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setBackgroundColor(Color.BLUE);
		
		for (int i = 0; i < cars.length; i++) {
			multiRenderer.addXTextLabel(i, TCar[i]);
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(incomeRenderer);

		// Creating an intent to plot bar chart using dataset and
		// multipleRenderer
		Intent intent = ChartFactory.getBarChartIntent(getBaseContext(),
				dataset, multiRenderer, Type.STACKED);

		// Start Activity
		startActivity(intent);

	}
}
