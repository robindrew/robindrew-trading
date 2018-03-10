package com.robindrew.trading.price.candle.window;

import java.util.List;

import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IMovingTimeWindow {

	UnitChrono getInterval();

	/**
	 * Returns the size of the window, that is the number of candles in the window.
	 * @return the size of the window, that is the number of candles in the window.
	 */
	int getSize();

	/**
	 * Returns the list of candles within the given window.
	 * @return the list of candles within the given window.
	 */
	List<IPriceCandle> getPriceCandleWindow();

}
