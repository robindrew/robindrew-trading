package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;

public interface IPcfFile extends IPcfSource {

	File getFile();

}
