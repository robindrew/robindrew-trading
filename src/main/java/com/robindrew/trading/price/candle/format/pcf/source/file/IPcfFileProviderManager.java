package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;

public interface IPcfFileProviderManager extends IPcfSourceProviderManager {

	File getRootDirectory();

	File getDirectory(IInstrument instrument);

}
