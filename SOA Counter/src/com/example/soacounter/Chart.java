package com.example.soacounter;

import java.util.ArrayList;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

import com.example.soacounter.CounterDatabaseAdapter.CounterHelper;

public class Chart {
	private CounterDatabaseAdapter dbAdapter;
	private Cursor cursor;

	public Chart(Context context) {
		dbAdapter = new CounterDatabaseAdapter(context);
		cursor = dbAdapter.getCounts();
		Log.d("soa", "Cursor rows: " + cursor.getCount());
	}

	public XYMultipleSeriesDataset getBarDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		ArrayList<String> legendTitles = new ArrayList<String>();
		legendTitles.add("Car");
		legendTitles.add("Bus");
		legendTitles.add("Truck");
		int i = 0;
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			Log.d("soa", "cursor iteration: " + cursor.getPosition());
			CategorySeries series = new CategorySeries(legendTitles.get(i));
			series.add((double) cursor.getInt(cursor
					.getColumnIndex(CounterHelper.CAR)));
			series.add((double) cursor.getInt(cursor
					.getColumnIndex(CounterHelper.BUS)));
			series.add((double) cursor.getInt(cursor
					.getColumnIndex(CounterHelper.TRUCK)));

			dataset.addSeries(series.toXYSeries());
			i++;
			cursor.moveToNext();
		}
		return dataset;
	}

	public XYMultipleSeriesRenderer getBarRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(20);
		renderer.setChartTitleTextSize(22);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setMargins(new int[] { 30, 40, 15, 0 });
		renderer.setZoomButtonsVisible(true);

		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.BLUE);
		renderer.addSeriesRenderer(r);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(15);

		r = new SimpleSeriesRenderer();
		r.setColor(Color.RED);
		renderer.addSeriesRenderer(r);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(15);

		r = new SimpleSeriesRenderer();
		r.setColor(Color.GREEN);
		renderer.addSeriesRenderer(r);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(15);
		return renderer;
	}

	public void chartSettings(XYMultipleSeriesRenderer renderer) {
		renderer.setChartTitle("Top 3 - Traffic counter");
		renderer.setXAxisMin(0);
		renderer.setXAxisMax(4);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(10);
		cursor.moveToFirst();
		renderer.addXTextLabel(1,
				cursor.getString(cursor.getColumnIndex(CounterHelper.PLACE)));
		cursor.moveToNext();
		renderer.addXTextLabel(2,
				cursor.getString(cursor.getColumnIndex(CounterHelper.PLACE)));
		cursor.moveToNext();
		renderer.addXTextLabel(3,
				cursor.getString(cursor.getColumnIndex(CounterHelper.PLACE)));
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setBarSpacing(0.5);
		renderer.setXTitle("Places");
		renderer.setYTitle("Vehicles");
		renderer.setLegendTextSize(20);
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.GRAY);
		renderer.setXLabels(0);
	}

}
