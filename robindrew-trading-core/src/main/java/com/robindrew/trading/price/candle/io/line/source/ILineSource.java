package com.robindrew.trading.price.candle.io.line.source;

import com.robindrew.trading.util.io.INamedCloseable;

public interface ILineSource extends INamedCloseable {

    String getNextLine();
}
