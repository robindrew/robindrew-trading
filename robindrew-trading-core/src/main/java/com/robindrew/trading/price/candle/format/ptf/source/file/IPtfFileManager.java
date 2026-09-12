package com.robindrew.trading.price.candle.format.ptf.source.file;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceManager;
import java.io.File;

public interface IPtfFileManager extends IPtfSourceManager {

    File getRootDirectory();
}
