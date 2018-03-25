package com.robindrew.trading.price.candle.charts.jfreechart;

import static java.util.Collections.emptyList;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultOHLCDataset;

import com.robindrew.common.image.ImageFormat;
import com.robindrew.common.io.Files;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instruments;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.charts.IPriceCandleChart;
import com.robindrew.trading.price.candle.charts.IPriceCandleChartBuilder;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.candle.interval.PriceCandleIntervals;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamSourceBuilder;
import com.robindrew.trading.provider.ITradeDataProvider;
import com.robindrew.trading.provider.TradeDataProvider;

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

	public static void main(String[] args) {
		IInstrument instrument = Instruments.GBP_USD;
		ITradeDataProvider provider = TradeDataProvider.ACTIVETICK;

		File directory = new File("C:\\development\\repository\\git\\robindrew-public\\robindrew-trading-data\\data\\pcf");
		IPcfSourceManager source = new PcfFileManager(directory, provider);
		IPcfSourceSet set = source.getSourceSet(instrument);
		LocalDateTime from = LocalDateTime.of(2018, 02, 01, 0, 0);
		LocalDateTime to = LocalDateTime.of(2018, 02, 28, 23, 59);
		Set<? extends IPcfSource> sources = set.getSources(from, to);
		IPriceCandleStreamSource stream = new PriceCandleStreamSourceBuilder().setPcfSources(sources).get();
		stream = new PriceCandleIntervalStreamSource(stream, PriceCandleIntervals.DAILY);
		List<IPriceCandle> list = PriceCandles.drainToList(stream);

		JFreePriceCandleChartBuilder builder = new JFreePriceCandleChartBuilder();
		builder.setCandles(list);
		builder.setDimensions(1024, 768);
		builder.setInstrument(instrument);
		IPriceCandleChart chart = builder.get();
		chart.writeTo(Files.asByteSink(new File("C:\\temp\\chart\\chart.png")), ImageFormat.PNG);
	}
}