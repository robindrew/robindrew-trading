package com.robindrew.trading.price.candle.format.ptf.source;

import static com.robindrew.trading.provider.TradeDataProvider.FXCM;

import java.io.File;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instruments;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileManager;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.provider.ITradeDataProvider;

public class PtfToPcfConverter {

	private static final Logger log = LoggerFactory.getLogger(PtfToPcfConverter.class);

	private final IPtfSourceManager ptf;
	private final PcfFileManager pcf;

	public PtfToPcfConverter(IPtfSourceManager ptf, PcfFileManager pcf) {
		this.ptf = Check.notNull("ptf", ptf);
		this.pcf = Check.notNull("pcf", pcf);
	}

	public void convert(ITradeDataProvider provider, IInstrument instrument) {

		// Get the instrument
		log.info("Converting Instrument: {}", instrument);

		IPtfSourceSet set = ptf.getSourceSet(instrument, provider);
		Set<? extends IPtfSource> sources = set.getSources();
		if (sources.isEmpty()) {
			return;
		}

		// Get the sources for the instrument
		IPriceCandleStreamSource source = new PtfSourcesStreamSource(sources);

		// We are converting to minute price candles
		IPriceInterval interval = PriceIntervals.MINUTELY;
		source = new PriceCandleIntervalStreamSource(source, interval);

		// Output directory
//		File directory = PcfFileManager.getDirectory(provider, instrument, outputDir);
//		if (directory.exists()) {
//			log.info("Output directory already exists, skipping: {}", directory);
//			return;
//		}
//		directory.mkdirs();
//
	}
}
