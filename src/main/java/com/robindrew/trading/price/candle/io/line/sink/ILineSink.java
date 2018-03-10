package com.robindrew.trading.price.candle.io.line.sink;

import com.robindrew.common.io.INamedCloseable;

public interface ILineSink extends INamedCloseable {

	void putNextLine(String line);

}
