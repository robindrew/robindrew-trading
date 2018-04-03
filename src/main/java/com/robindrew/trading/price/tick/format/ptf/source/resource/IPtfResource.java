package com.robindrew.trading.price.tick.format.ptf.source.resource;

import java.net.URL;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;

public interface IPtfResource extends IPcfSource {

	URL getURL();

}
