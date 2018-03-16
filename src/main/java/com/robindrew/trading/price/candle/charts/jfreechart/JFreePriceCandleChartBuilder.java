package com.robindrew.trading.price.candle.charts.jfreechart;

import static java.util.Collections.emptyList;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultOHLCDataset;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.charts.IPriceCandleChart;
import com.robindrew.trading.price.candle.charts.IPriceCandleChartBuilder;

public class JFreePriceCandleChartBuilder implements IPriceCandleChartBuilder {

	// Mandatory
	private IInstrument instrument;
	// Mandatory
	private List<IPriceCandle> candles = emptyList();

	// Optional
	private int width = 800;
	// Optional
	private int height = 600;
	// Optional
	private String dateAxisLabel = "Date";
	// Optional
	private String priceAxisLabel = "Price";
	// Optional
	private Font font = null;

	@Override
	public IPriceCandleChartBuilder setCandles(List<IPriceCandle> candles) {
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles");
		}
		this.candles = candles;
		return this;
	}

	@Override
	public IPriceCandleChartBuilder setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	public IPriceCandleChartBuilder setInstrument(IInstrument instrument) {
		if (instrument == null) {
			throw new NullPointerException("instrument");
		}
		this.instrument = instrument;
		return this;
	}

	@Override
	public IPriceCandleChartBuilder setDateAxisLabel(String label) {
		if (label.isEmpty()) {
			throw new IllegalArgumentException("label is empty");
		}
		dateAxisLabel = label;
		return this;
	}

	@Override
	public IPriceCandleChartBuilder setPriceAxisLabel(String label) {
		if (label.isEmpty()) {
			throw new IllegalArgumentException("label is empty");
		}
		priceAxisLabel = label;
		return this;
	}

	@Override
	public IPriceCandleChart get() {
		if (instrument == null) {
			throw new NullPointerException("instrument");
		}
		if (candles.isEmpty()) {
			throw new NullPointerException("candles is empty");
		}

		OHLCDataItemAdaptor adaptor = new OHLCDataItemAdaptor();

		DateAxis domainAxis = new DateAxis(dateAxisLabel);
		NumberAxis rangeAxis = new NumberAxis(priceAxisLabel);
		CandlestickRenderer renderer = new CandlestickRenderer();
		DefaultOHLCDataset dataset = adaptor.toDataset(instrument.getName(), candles);

		XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

		// Do some setting up, see the API Doc
		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setDrawVolume(false);
		rangeAxis.setAutoRangeIncludesZero(false);
		domainAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

		// Now create the chart and chart panel
		JFreeChart chart = new JFreeChart(instrument.getName(), font, plot, false);
		return new JFreePriceCandleChart(chart, width, height);
	}
}