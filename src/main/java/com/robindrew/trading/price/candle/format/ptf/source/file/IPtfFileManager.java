package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceManager;
import com.robindrew.trading.provider.ITradeDataProvider;

public interface IPtfFileManager extends IPtfSourceManager {

	File getRootDirectory();

	File getDirectory(ITradeDataProvider provider, IInstrument instrument);

}
