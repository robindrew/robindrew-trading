package com.robindrew.trading.price.precision;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.robindrew.trading.price.candle.tick.TickPriceCandle;

public class PricePrecisionTest {

	@Test
	public void toBigDecimal() {

		long timestamp = 123456789;
		int decimalPlaces = 2;

		TickPriceCandle tick = new TickPriceCandle(10089, 10103, timestamp, decimalPlaces);

		IPricePrecision precision = new PricePrecision(decimalPlaces);

		Assert.assertEquals(new BigDecimal("100.89"), precision.toBigDecimal(tick.getBidPrice()));
		Assert.assertEquals(new BigDecimal("101.03"), precision.toBigDecimal(tick.getAskPrice()));
	}

	@Test
	public void normalise() {

		long timestamp = 123456789;
		int decimalPlaces = 1;

		TickPriceCandle tick = new TickPriceCandle(10089, 10103, timestamp, decimalPlaces);

		IPricePrecision precision = new PricePrecision(1);

		Assert.assertEquals(new BigDecimal("1008.9"), precision.toBigDecimal(tick.getBidPrice()));
		Assert.assertEquals(new BigDecimal("1010.3"), precision.toBigDecimal(tick.getAskPrice()));
	}

}
