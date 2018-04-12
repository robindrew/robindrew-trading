package com.robindrew.trading.price.candle.charts.jfreechart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;

import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * An adaptor for Open/High/Low/Close data items (candles).
 */
public class OHLCDataItemAdaptor {

	public OHLCDataItem toItem(IPriceCandle candle) {

		Date date = new Date(candle.getOpenTime());
		double open = candle.getMidOpenPrice();
		double high = candle.getMidHighPrice();
		double low = candle.getMidLowPrice();
		double close = candle.getMidClosePrice();
		double volume = 0;

		return new OHLCDataItem(date, open, high, low, close, volume);
	}

	public List<OHLCDataItem> toList(List<IPriceCandle> candles) {
		List<OHLCDataItem> items = new ArrayList<>();
		for (IPriceCandle candle : candles) {
			items.add(toItem(candle));
		}
		return items;
	}

	public DefaultOHLCDataset toDataset(String instrument, List<IPriceCandle> candles) {
		List<OHLCDataItem> items = toList(candles);
		OHLCDataItem[] array = items.toArray(new OHLCDataItem[items.size()]);
		return new DefaultOHLCDataset(instrument, array);
	}
}