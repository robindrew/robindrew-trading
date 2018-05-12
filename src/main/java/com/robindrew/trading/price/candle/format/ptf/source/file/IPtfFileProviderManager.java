package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceProviderManager;

public interface IPtfFileProviderManager extends IPtfSourceProviderManager {

	File getRootDirectory();

	File getDirectory(IInstrument instrument);

}
