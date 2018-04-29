package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.provider.ITradeDataProvider;

public interface IPcfFileManager extends IPcfSourceManager {

	File getDirectory(ITradeDataProvider provider, IInstrument instrument);

}
