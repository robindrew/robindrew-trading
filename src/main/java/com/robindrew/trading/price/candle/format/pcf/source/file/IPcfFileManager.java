package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;

public interface IPcfFileManager extends IPcfSourceManager {

	File getRootDirectory();
}
