package com.robindrew.trading.price.candle.merger;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;

public class PriceCandleMergerTest {

	@Test
	public void testSimpleMerge() {

		IPriceCandle candle1 = new PriceCandle(10, 20, 5, 15, 100, 101, 2);
		IPriceCandle candle2 = new PriceCandle(15, 30, 13, 29, 101, 102, 2);

		IPriceCandle merged = new PriceCandleMerger().merge(candle1, candle2);

		Assert.assertEquals(candle1.getOpenTime(), merged.getOpenTime());
		Assert.assertEquals(candle2.getCloseTime(), merged.getCloseTime());

		Assert.assertEquals(candle1.getOpenPrice(), merged.getOpenPrice());
		Assert.assertEquals(candle2.getClosePrice(), merged.getClosePrice());

		Assert.assertEquals(candle2.getHighPrice(), merged.getHighPrice());
		Assert.assertEquals(candle1.getLowPrice(), merged.getLowPrice());
	}

	@Test
	public void testListMerge() {

		IPriceCandle candle1 = new PriceCandle(10, 20, 5, 15, 100, 101, 2);
		IPriceCandle candle2 = new PriceCandle(15, 30, 13, 29, 101, 102, 2);
		IPriceCandle candle3 = new PriceCandle(29, 33, 28, 28, 102, 103, 2);
		IPriceCandle candle4 = new PriceCandle(28, 29, 6, 12, 103, 104, 2);

		IPriceCandle merged = new PriceCandleMerger().merge(Arrays.asList(candle1, candle2, candle3, candle4));

		Assert.assertEquals(candle1.getOpenTime(), merged.getOpenTime());
		Assert.assertEquals(candle4.getCloseTime(), merged.getCloseTime());

		Assert.assertEquals(candle1.getOpenPrice(), merged.getOpenPrice());
		Assert.assertEquals(candle4.getClosePrice(), merged.getClosePrice());

		Assert.assertEquals(candle3.getHighPrice(), merged.getHighPrice());
		Assert.assertEquals(candle1.getLowPrice(), merged.getLowPrice());
	}

}
