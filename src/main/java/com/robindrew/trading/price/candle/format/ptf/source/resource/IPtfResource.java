package com.robindrew.trading.price.candle.format.ptf.source.resource;

import java.net.URL;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;

public interface IPtfResource extends IPtfSource {

	URL getURL();

}
