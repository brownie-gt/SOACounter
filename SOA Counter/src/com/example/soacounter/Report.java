package com.example.soacounter;

import java.util.ArrayList;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class Report {


	private static final int BARS = 2;
	private static final int PLACES = 3;

	public XYMultipleSeriesDataset getTruitonBarDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		ArrayList<String> legendTitles = new ArrayList<String>();
		legendTitles.add("Car");
		legendTitles.add("Bus");
//		legendTitles.add("Truck");

		for (int i = 0; i < BARS; i++) {
			CategorySeries series = new CategorySeries(legendTitles.get(i));
			for (int k = 0; k < PLACES; k++) {
				series.add((double) i + (double) k);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	public XYMultipleSeriesRenderer getTruitonBarRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setMargins(new int[] { 30, 40, 15, 0 });
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.BLUE);
		renderer.addSeriesRenderer(r);
		r = new SimpleSeriesRenderer();
		r.setColor(Color.RED);
		renderer.addSeriesRenderer(r);
		return renderer;
	}

	public void myChartSettings(XYMultipleSeriesRenderer renderer) {
		renderer.setChartTitle("Top 3 - Traffic counter");
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(10.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(210);
		renderer.addXTextLabel(1, "Place One");
		renderer.addXTextLabel(2, "Place Two");
		renderer.addXTextLabel(3, "Place Three");
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setBarSpacing(0.5);
		renderer.setXTitle("Places");
		renderer.setYTitle("Vehicles");
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.GRAY);
		renderer.setXLabels(0); // sets the number of integer labels to appear
	}
	
}
