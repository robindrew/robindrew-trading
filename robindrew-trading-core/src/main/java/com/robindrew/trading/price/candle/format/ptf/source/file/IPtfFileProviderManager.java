package com.robindrew.trading.price.candle.format.ptf.source.file;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceProviderManager;
import java.io.File;

public interface IPtfFileProviderManager extends IPtfSourceProviderManager {

    File getRootDirectory();

    File getDirectory(IInstrument instrument);
}
