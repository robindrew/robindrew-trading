package com.robindrew.trading.price.candle.format.ptf.source.resource;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import java.net.URL;

public interface IPtfResource extends IPtfSource {

    URL getURL();
}
