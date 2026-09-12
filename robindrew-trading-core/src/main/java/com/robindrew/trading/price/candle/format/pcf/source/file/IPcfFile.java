package com.robindrew.trading.price.candle.format.pcf.source.file;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import java.io.File;

public interface IPcfFile extends IPcfSource {

    File getFile();
}
