package com.robindrew.trading.price.candle.checker;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleChecker {

	boolean check(IPriceCandle previous, IPriceCandle current);

}
