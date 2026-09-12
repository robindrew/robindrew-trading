package com.robindrew.trading.price.candle.format.pcf.source.resource;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import java.net.URL;

public interface IPcfResource extends IPcfSource {

    URL getURL();
}
