package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;

public interface IPtfFile extends IPtfSource {

	File getFile();

}
