package com.robindrew.trading.price.calc;

import static com.google.common.base.Optional.absent;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Optional;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.TickPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.indicator.SimpleMovingAverage;

public class SimpleMovingAverageTest {

	private int decimalPlaces = 2;

	@Test
	public void calculate() {

		try (SimpleMovingAverage average = new SimpleMovingAverage(10)) {

			nextPrice(average, 1, 2227, 0);
			nextPrice(average, 2, 2219, 0);
			nextPrice(average, 3, 2208, 0);
			nextPrice(average, 4, 2217, 0);
			nextPrice(average, 5, 2218, 0);
			nextPrice(average, 6, 2213, 0);
			nextPrice(average, 7, 2223, 0);
			nextPrice(average, 8, 2243, 0);
			nextPrice(average, 9, 2224, 0);

			nextPrice(average, 10, 2229, 2222);
			nextPrice(average, 11, 2215, 2221);
			nextPrice(average, 12, 2239, 2223);
			nextPrice(average, 13, 2238, 2226);
			nextPrice(average, 14, 2261, 2230);
			nextPrice(average, 15, 2336, 2242);
			nextPrice(average, 16, 2405, 2261);
			nextPrice(average, 17, 2375, 2277);
			nextPrice(average, 18, 2383, 2291);
			nextPrice(average, 19, 2395, 2308);
			nextPrice(average, 20, 2363, 2321);

			nextPrice(average, 21, 2382, 2338);
			nextPrice(average, 22, 2387, 2353);
			nextPrice(average, 23, 2365, 2365);
			nextPrice(average, 24, 2319, 2371);
			nextPrice(average, 25, 2310, 2368);
			nextPrice(average, 26, 2333, 2361);
			nextPrice(average, 27, 2268, 2351);
			nextPrice(average, 28, 2310, 2343);
			nextPrice(average, 29, 2240, 2328);
			nextPrice(average, 30, 2217, 2313);
		}
	}

	private void nextPrice(SimpleMovingAverage average, int number, int decimal, int expectedValue) {

		IPriceCandle candle = new TickPriceCandle(decimal, decimal, number, decimalPlaces);
		average.putNextCandle(candle);

		Optional<Decimal> expected = absent();
		if (expectedValue > 0) {
			expected = Optional.of(new Decimal(expectedValue, decimalPlaces));
		}
		assertEquals(expected, average.getIndicator());
	}
}
