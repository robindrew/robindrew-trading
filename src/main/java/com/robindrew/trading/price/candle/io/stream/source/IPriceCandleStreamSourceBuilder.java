package com.robindrew.trading.price.candle.io.stream.source;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.checker.IPriceCandleChecker;
import com.robindrew.trading.price.candle.filter.IPriceCandleFilter;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSourceBuilder;
import com.robindrew.trading.price.candle.modifier.IPriceCandleModifier;

public interface IPriceCandleStreamSourceBuilder extends Supplier<IPriceCandleStreamSource> {

	IPriceCandleStreamSourceBuilder setBaseSource(IPriceCandleStreamSource source);

	IPriceCandleStreamSourceBuilder setPcfSources(Collection<? extends IPcfSource> sources);

	IPriceCandleStreamSourceBuilder setPcfFile(String filename);

	IPriceCandleStreamSourceBuilder setPcfSource(IPcfSource source);

	IPriceCandleStreamSourceBuilder setCandles(List<IPriceCandle> candles);

	IPriceCandleStreamSourceBuilder filterBy(IPriceCandleFilter filter);

	IPriceCandleStreamSourceBuilder between(LocalDateTime from, LocalDateTime to);

	IPriceCandleStreamSourceBuilder between(LocalDate from, LocalDate to);

	IPriceCandleStreamSourceBuilder limit(int maxCandles);

	IPriceCandleStreamSourceBuilder modifyWith(IPriceCandleModifier modifier);

	IPriceCandleStreamSourceBuilder multiplyBy(int multiplier);

	IPriceCandleStreamSourceBuilder divideBy(int divisor);

	IPriceCandleStreamSourceBuilder checkWith(IPriceCandleChecker checker);

	IPriceCandleStreamSourceBuilder checkSortedByDate();

	IPriceCandleStreamSourceBuilder checkSortedBy(Comparator<IPriceCandle> comparator);

	IPriceCandleStreamSourceBuilder checkSanity(double maxPercentDiff);

	IPriceCandleStreamSourceBuilder filterConsecutive(int logThreshold);

	IPriceCandleStreamSourceBuilder setInterval(IPriceInterval interval);

	IPriceCandleListSourceBuilder asListSourceBuilder();

	List<IPriceCandle> getList();

}