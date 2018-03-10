package com.robindrew.trading.price.candle.charts;

import com.google.common.io.ByteSink;
import com.robindrew.common.image.ImageFormat;

public interface IPriceCandleChart {

	void writeTo(ByteSink sink, ImageFormat format);

}
