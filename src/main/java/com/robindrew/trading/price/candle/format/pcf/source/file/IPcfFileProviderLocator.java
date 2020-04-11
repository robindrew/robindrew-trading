package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderLocator;

public interface IPcfFileProviderLocator extends IPcfSourceProviderLocator {

	File getRootDirectory();
}
