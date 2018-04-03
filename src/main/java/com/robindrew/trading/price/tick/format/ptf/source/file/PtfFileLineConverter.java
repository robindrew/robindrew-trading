package com.robindrew.trading.price.tick.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.line.filter.ILineFilter;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTickDateComparator;
import com.robindrew.trading.price.tick.checker.PriceTickRangeChecker;
import com.robindrew.trading.price.tick.filter.PriceTickConsecutiveFilter;
import com.robindrew.trading.price.tick.format.ptf.PtfFormat;
import com.robindrew.trading.price.tick.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.tick.format.ptf.source.IPtfSourceManager;
import com.robindrew.trading.price.tick.io.list.filter.PriceTickListDuplicateFilter;
import com.robindrew.trading.price.tick.io.list.source.IPriceTickListSource;
import com.robindrew.trading.price.tick.io.stream.source.IPriceTickStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickCheckerStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickDirectoryStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickFilteredStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickIntervalStreamToListSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickLoggedStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickModifierStreamSource;
import com.robindrew.trading.price.tick.line.parser.IPriceTickLineParser;
import com.robindrew.trading.price.tick.modifier.PriceTickMultiplyModifier;

public class PtfFileLineConverter {

	private static final Logger log = LoggerFactory.getLogger(PtfFileLineConverter.class);

	private final IPriceTickLineParser parser;
	private final ILineFilter filter;
	private final IPtfSourceManager manager;
	private boolean verify = true;
	private int loggingFrequency = 1000;
	private int multiplier = 0;
	private int minPrice = 1;
	private int maxPrice = Integer.MAX_VALUE;

	public PtfFileLineConverter(IPtfSourceManager manager, IPriceTickLineParser parser, ILineFilter filter) {
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
		try (IPriceTickListSource source = createSource(fromDirectory, interval)) {
			while (true) {

				// 1. Read all the ticks in to one big list!
				List<IPriceTick> ticks = source.getNextTicks();
				if (ticks.isEmpty()) {
					break;
				}

				// 2. Sort the ticks
				ticks = new PriceTickListDuplicateFilter().filter(ticks);
				Collections.sort(ticks, new PriceTickDateComparator());

				// 3. Write the PCF tick file
				LocalDate month = PtfFormat.getMonth(ticks.get(0));
				IPtfSource file = manager.getSourceSet(instrument).getSource(month, true);
				if (file.exists()) {
					log.warn("Source already exists: {}", file.getName());
					continue;
				}
				file.write(ticks);

				// 4. Verify the PCF tick file
				if (verify) {
					List<IPriceTick> written = file.read();
					if (!ticks.equals(written)) {
						throw new IllegalStateException("Ticks do not match!!");
					}
				}
			}
		}
	}

	private PriceTickIntervalStreamToListSource createSource(File fromDirectory, IPriceInterval interval) {
		IPriceTickStreamSource source = new PriceTickDirectoryStreamSource(fromDirectory, parser, filter);
		source = new PriceTickFilteredStreamSource(source, new PriceTickConsecutiveFilter(1000));
		source = new PriceTickModifierStreamSource(source, new PriceTickMultiplyModifier(multiplier));
		source = new PriceTickLoggedStreamSource(source, loggingFrequency);
		source = new PriceTickCheckerStreamSource(source, new PriceTickRangeChecker(minPrice, maxPrice));
		return new PriceTickIntervalStreamToListSource(source, interval);
	}

}
