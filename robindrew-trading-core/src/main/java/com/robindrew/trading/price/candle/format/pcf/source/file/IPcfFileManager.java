package com.robindrew.trading.price.candle.format.pcf.source.file;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import java.io.File;

public interface IPcfFileManager extends IPcfSourceManager {

    File getRootDirectory();
}
