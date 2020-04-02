package com.robindrew.trading.price.calc;

import static com.google.common.base.Optional.absent;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Optional;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.tick.TickPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.indicator.RelativeStrengthIndex;

public class RelativeStrengthIndexTest {

	private int decimalPlaces = 0;

	@Test
	public void calculate() {

		try (RelativeStrengthIndex index = new RelativeStrengthIndex(14)) {

			nextPrice(index, 1, 443389, 0);
			nextPrice(index, 2, 440902, 0);
			nextPrice(index, 3, 441497, 0);
			nextPrice(index, 4, 436124, 0);
			nextPrice(index, 5, 443278, 0);
			nextPrice(index, 6, 448264, 0);
			nextPrice(index, 7, 450955, 0);
			nextPrice(index, 8, 454245, 0);
			nextPrice(index, 9, 458433, 0);
			nextPrice(index, 10, 460826, 0);
			nextPrice(index, 11, 458931, 0);
			nextPrice(index, 12, 460328, 0);
			nextPrice(index, 13, 456140, 0);
			nextPrice(index, 14, 462820, 0);
			nextPrice(index, 15, 462820, 7054);
			nextPrice(index, 16, 460028, 6633);
			nextPrice(index, 17, 460328, 6656);
			nextPrice(index, 18, 464116, 6942);
			nextPrice(index, 19, 462222, 6636);
			nextPrice(index, 20, 456439, 5798);
			nextPrice(index, 21, 462122, 6293);
			nextPrice(index, 22, 462521, 6326);
			nextPrice(index, 23, 457137, 5606);
			nextPrice(index, 24, 464515, 6238);
			nextPrice(index, 25, 457835, 5471);

		}
	}

	private void nextPrice(RelativeStrengthIndex index, int number, int decimal, int expectedValue) {

		IPriceCandle candle = new TickPriceCandle(decimal, decimal, number, decimalPlaces);
		index.putNextCandle(candle);

		Optional<Decimal> expected = absent();
		if (expectedValue > 0) {
			expected = Optional.of(new Decimal(expectedValue, 2));
		}
		assertEquals(expected, index.getIndicator());

	}

}
