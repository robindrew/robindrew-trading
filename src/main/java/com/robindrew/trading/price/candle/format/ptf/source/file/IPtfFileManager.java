package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceManager;

public interface IPtfFileManager extends IPtfSourceManager {

	File getRootDirectory();

}
