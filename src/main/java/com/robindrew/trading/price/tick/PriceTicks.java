package com.robindrew.trading.price.tick;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import com.robindrew.trading.price.close.IClosePrice;
import com.robindrew.trading.price.tick.checker.PriceTickSortedChecker;
import com.robindrew.trading.price.tick.filter.PriceTickDateFilter;
import com.robindrew.trading.price.tick.filter.PriceTickDateTimeFilter;
import com.robindrew.trading.price.tick.io.list.source.IPriceTickListSource;
import com.robindrew.trading.price.tick.io.stream.source.IPriceTickStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickCheckerStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickFilteredStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickStreamToListSource;
import com.robindrew.trading.price.tick.io.stream.source.iterator.PriceTickStreamSourceIterable;
import com.robindrew.trading.price.tick.io.stream.source.iterator.PriceTickStreamSourceIterator;

public class PriceTicks {

	public static double getAverage(double total, int count) {
		return (count <= 1) ? total : (total / count);
	}

	public static double getSmoothingConstant(int periods) {
		if (periods < 1) {
			throw new IllegalArgumentException("periods=" + periods);
		}
		return 2.0f / (periods + 1);
	}

	public static int getChange(IClosePrice previous, IClosePrice current) {
		return current.getClosePrice() - previous.getClosePrice();
	}

	public static double getPercentDifference(double price1, double price2) {
		double diff = (price1 > price2) ? price1 / price2 : price2 / price1;
		// Convert to a percentage ...
		return (diff * 100.0) - 100.0;
	}

	public static Iterator<IPriceTick> iterator(IPriceTickStreamSource source) {
		return new PriceTickStreamSourceIterator(source);
	}

	public static Iterable<IPriceTick> iterable(IPriceTickStreamSource source) {
		return new PriceTickStreamSourceIterable(source);
	}

	public static final List<IPriceTick> drainToList(IPriceTickStreamSource source) {
		try (IPriceTickListSource list = new PriceTickStreamToListSource(source)) {
			return list.getNextTicks();
		}
	}

	public static IPriceTickStreamSource filterByDates(IPriceTickStreamSource source, LocalDateTime from, LocalDateTime to) {
		return new PriceTickFilteredStreamSource(source, new PriceTickDateTimeFilter(from, to));
	}

	public static IPriceTickStreamSource filterByDates(IPriceTickStreamSource source, LocalDate from, LocalDate to) {
		return new PriceTickFilteredStreamSource(source, new PriceTickDateFilter(from, to));
	}

	public static IPriceTickStreamSource checkSorted(IPriceTickStreamSource source) {
		return new PriceTickCheckerStreamSource(source, new PriceTickSortedChecker());
	}

}
