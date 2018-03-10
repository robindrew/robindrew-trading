package com.robindrew.trading.price.candle.format.pcf.source.resource;

import java.net.URL;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;

public interface IPcfResource extends IPcfSource {

	URL getURL();

}
