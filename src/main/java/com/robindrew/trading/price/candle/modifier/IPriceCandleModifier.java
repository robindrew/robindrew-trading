package com.robindrew.trading.price.candle.modifier;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleModifier {

	IPriceCandle modify(IPriceCandle candle);

}
