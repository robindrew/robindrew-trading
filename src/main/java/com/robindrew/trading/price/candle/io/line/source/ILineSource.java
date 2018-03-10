package com.robindrew.trading.price.candle.io.line.source;

import com.robindrew.common.io.INamedCloseable;

public interface ILineSource extends INamedCloseable {

	String getNextLine();

}
