package com.robindrew.trading.price.candle.format.pcf.source.file;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import java.io.File;

public interface IPcfFileProviderManager extends IPcfSourceProviderManager {

    File getRootDirectory();

    File getDirectory(IInstrument instrument);
}
