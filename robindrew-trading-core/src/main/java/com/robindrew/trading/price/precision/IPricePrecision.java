package com.robindrew.trading.price.precision;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.math.BigDecimal;

public interface IPricePrecision {

    int getDecimalPlaces();

    int toBigInt(BigDecimal price);

    BigDecimal toBigDecimal(int price);

    IPriceCandle normalize(IPriceCandle candle);
}
