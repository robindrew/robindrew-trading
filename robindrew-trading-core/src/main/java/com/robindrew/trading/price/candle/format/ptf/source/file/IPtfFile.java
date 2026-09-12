package com.robindrew.trading.price.candle.format.ptf.source.file;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import java.io.File;

public interface IPtfFile extends IPtfSource {

    File getFile();
}
