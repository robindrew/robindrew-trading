package com.robindrew.trading.price.tick.io.stream.source;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.modifier.IPriceTickModifier;

public class PriceTickModifierStreamSource implements IPriceTickStreamSource {

	private final IPriceTickStreamSource source;
	private final IPriceTickModifier modifier;

	public PriceTickModifierStreamSource(IPriceTickStreamSource source, IPriceTickModifier modifier) {
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
	public IPriceTick getNextTick() {
		IPriceTick next = source.getNextTick();
		if (next == null) {
			return next;
		}

		// Apply modifier
		return modifier.modify(next);
	}

}
