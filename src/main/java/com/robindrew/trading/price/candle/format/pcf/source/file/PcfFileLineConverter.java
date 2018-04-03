package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandleDateComparator;
import com.robindrew.trading.price.candle.checker.PriceCandleRangeChecker;
import com.robindrew.trading.price.candle.filter.PriceCandleConsecutiveFilter;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.list.filter.PriceCandleListDuplicateFilter;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSource;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleCheckerStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleDirectoryStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleFilteredStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamToListSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleLoggedStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleModifierStreamSource;
import com.robindrew.trading.price.candle.line.filter.ILineFilter;
import com.robindrew.trading.price.candle.line.parser.IPriceCandleLineParser;
import com.robindrew.trading.price.candle.modifier.PriceCandleMultiplyModifier;

public class PcfFileLineConverter {

	private static final Logger log = LoggerFactory.getLogger(PcfFileLineConverter.class);

	private final IPriceCandleLineParser parser;
	private final ILineFilter filter;
	private final IPcfSourceManager manager;
	private boolean verify = true;
	private int loggingFrequency = 1000;
	private int multiplier = 0;
	private int minPrice = 1;
	private int maxPrice = Integer.MAX_VALUE;

	public PcfFileLineConverter(IPcfSourceManager manager, IPriceCandleLineParser parser, ILineFilter filter) {
		this.manager = Check.notNull("manager", manager);
		this.parser = Check.notNull("parser", parser);
		this.filter = Check.notNull("filter", filter);
	}

	public int getLoggingFrequency() {
		return loggingFrequency;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}

	public void setLoggingFrequency(int loggingFrequency) {
		this.loggingFrequency = loggingFrequency;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public void convert(IInstrument instrument, File fromDirectory) {
		Check.existsDirectory("fromDirectory", fromDirectory);

		IPriceInterval interval = PriceIntervals.MONTHLY;
		try (IPriceCandleListSource source = createSource(fromDirectory, interval)) {
			while (true) {

				// 1. Read all the candles in to one big list!
				List<IPriceCandle> candles = source.getNextCandles();
				if (candles.isEmpty()) {
					break;
				}

				// 2. Sort the candles
				candles = new PriceCandleListDuplicateFilter().filter(candles);
				Collections.sort(candles, new PriceCandleDateComparator());

				// 3. Write the PCF candle file
				LocalDate month = PcfFormat.getMonth(candles.get(0));
				IPcfSource file = manager.getSourceSet(instrument).getSource(month, true);
				if (file.exists()) {
					log.warn("Source already exists: {}", file.getName());
					continue;
				}
				file.write(candles);

				// 4. Verify the PCF candle file
				if (verify) {
					List<IPriceCandle> written = file.read();
					if (!candles.equals(written)) {
						throw new IllegalStateException("Candles do not match!!");
					}
				}
			}
		}
	}

	private PriceCandleIntervalStreamToListSource createSource(File fromDirectory, IPriceInterval interval) {
		IPriceCandleStreamSource source = new PriceCandleDirectoryStreamSource(fromDirectory, parser, filter);
		source = new PriceCandleFilteredStreamSource(source, new PriceCandleConsecutiveFilter(1000));
		source = new PriceCandleIntervalStreamSource(source, PriceIntervals.MINUTELY);
		source = new PriceCandleModifierStreamSource(source, new PriceCandleMultiplyModifier(multiplier));
		source = new PriceCandleLoggedStreamSource(source, loggingFrequency);
		source = new PriceCandleCheckerStreamSource(source, new PriceCandleRangeChecker(minPrice, maxPrice));
		return new PriceCandleIntervalStreamToListSource(source, interval);
	}

}
