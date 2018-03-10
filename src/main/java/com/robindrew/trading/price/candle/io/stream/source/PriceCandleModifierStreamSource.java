package com.robindrew.trading.price.candle.io.stream.source;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.modifier.IPriceCandleModifier;

public class PriceCandleModifierStreamSource implements IPriceCandleStreamSource {

	private final IPriceCandleStreamSource source;
	private final IPriceCandleModifier modifier;

	public PriceCandleModifierStreamSource(IPriceCandleStreamSource source, IPriceCandleModifier modifier) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (modifier == null) {
			throw new NullPointerException("modifier");
		}
		this.source = source;
		this.modifier = modifier;
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void close() {
		source.close();
	}

	@Override
	public IPriceCandle getNextCandle() {
		IPriceCandle next = source.getNextCandle();
		if (next == null) {
			return next;
		}

		// Apply modifier
		return modifier.modify(next);
	}

}
