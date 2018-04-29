package com.robindrew.trading.price.candle.io.list.sink;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiPredicate;

import com.robindrew.common.collect.ListPartitioner;
import com.robindrew.common.io.NamedCloseableDeletage;
import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * Batches candles by date. Candles starting on different days will be separated.
 */
public class DateBatchedPriceCandleListConsumer extends NamedCloseableDeletage<IPriceCandleListSink> implements IPriceCandleListSink {

	private final ListPartitioner<IPriceCandle> partitioner = new ListPartitioner<>(new DatePredicate());

	public DateBatchedPriceCandleListConsumer(IPriceCandleListSink delegate) {
		super(delegate);
	}

	@Override
	public void putNextCandles(List<? extends IPriceCandle> candles) {
		for (List<IPriceCandle> list : partitioner.partition(candles)) {
			getDelegate().putNextCandles(list);
		}
	}

	private static class DatePredicate implements BiPredicate<IPriceCandle, IPriceCandle> {

		@Override
		public boolean test(IPriceCandle candle1, IPriceCandle candle2) {
			LocalDate date1 = toDate(candle1);
			LocalDate date2 = toDate(candle2);
			return date1.equals(date2);
		}

		private LocalDate toDate(IPriceCandle candle) {
			return toLocalDateTime(candle.getOpenTime()).toLocalDate();
		}
	}

}
