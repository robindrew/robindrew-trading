package com.robindrew.trading.price.tick.format.ptf.source.file;

import java.io.File;

import com.robindrew.trading.price.tick.format.ptf.source.IPtfSource;

public interface IPtfFile extends IPtfSource {

	File getFile();

}
