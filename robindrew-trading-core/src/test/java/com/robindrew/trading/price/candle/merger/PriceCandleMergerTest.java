package com.robindrew.trading.price.candle.merger;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.MidPriceCandle;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceCandleMergerTest {

    @Test
    public void testSimpleMerge() {

        IPriceCandle candle1 = new MidPriceCandle(10, 20, 5, 15, 100, 101, 2, 0);
        IPriceCandle candle2 = new MidPriceCandle(15, 30, 13, 29, 101, 102, 2, 0);

        IPriceCandle merged = new PriceCandleMerger().merge(candle1, candle2);

        Assertions.assertEquals(candle1.getOpenTime(), merged.getOpenTime());
        Assertions.assertEquals(candle2.getCloseTime(), merged.getCloseTime());

        Assertions.assertEquals(candle1.getMidOpenPrice(), merged.getMidOpenPrice());
        Assertions.assertEquals(candle2.getMidClosePrice(), merged.getMidClosePrice());

        Assertions.assertEquals(candle2.getMidHighPrice(), merged.getMidHighPrice());
        Assertions.assertEquals(candle1.getMidLowPrice(), merged.getMidLowPrice());
    }

    @Test
    public void testListMerge() {

        IPriceCandle candle1 = new MidPriceCandle(10, 20, 5, 15, 100, 101, 2, 0);
        IPriceCandle candle2 = new MidPriceCandle(15, 30, 13, 29, 101, 102, 2, 0);
        IPriceCandle candle3 = new MidPriceCandle(29, 33, 28, 28, 102, 103, 2, 0);
        IPriceCandle candle4 = new MidPriceCandle(28, 29, 6, 12, 103, 104, 2, 0);

        IPriceCandle merged = new PriceCandleMerger().merge(Arrays.asList(candle1, candle2, candle3, candle4));

        Assertions.assertEquals(candle1.getOpenTime(), merged.getOpenTime());
        Assertions.assertEquals(candle4.getCloseTime(), merged.getCloseTime());

        Assertions.assertEquals(candle1.getMidOpenPrice(), merged.getMidOpenPrice());
        Assertions.assertEquals(candle4.getMidClosePrice(), merged.getMidClosePrice());

        Assertions.assertEquals(candle3.getMidHighPrice(), merged.getMidHighPrice());
        Assertions.assertEquals(candle1.getMidLowPrice(), merged.getMidLowPrice());
    }
}
