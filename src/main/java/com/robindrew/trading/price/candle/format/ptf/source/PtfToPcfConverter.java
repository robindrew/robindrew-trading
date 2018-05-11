package com.robindrew.trading.price.candle.format.ptf.source;

import static com.robindrew.trading.provider.TradeDataProvider.FXCM;

import java.io.File;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.lang.Args;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileStreamSink;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileManager;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.provider.ITradeDataProvider;

public class PtfToPcfConverter {

	private static final Logger log = LoggerFactory.getLogger(PtfToPcfConverter.class);

	public static void main(String[] array) {
		Args args = new Args(array);

		String fromDir = args.get("-f"); // "C:\\development\\data\\converted\\ptf"
		String toDir = args.get("-t"); // "C:\\development\\data\\converted\\pcf";

		IPtfSourceManager ptf = new PtfFileManager(new File(fromDir));
		IPcfFileManager pcf = new PcfFileManager(new File(toDir));

		PtfToPcfConverter converter = new PtfToPcfConverter(ptf, pcf);
		for (IInstrument instrument : ptf.getInstruments(FXCM)) {
			converter.convert(FXCM, instrument);
		}
	}

	private final IPtfSourceManager ptf;
	private final IPcfFileManager pcf;

	public PtfToPcfConverter(IPtfSourceManager ptf, IPcfFileManager pcf) {
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

		// We are converting to minute price candles
		IPriceInterval interval = PriceIntervals.MINUTELY;

		// Get the sources for the instrument
		try (IPriceCandleStreamSource source = createSource(sources, interval)) {

			// Output directory
			File directory = pcf.getDirectory(provider, instrument);
			if (directory.exists()) {
				log.info("Output directory already exists, skipping: {}", directory);
				return;
			}
			directory.mkdirs();

			try (PcfFileStreamSink sink = new PcfFileStreamSink(directory)) {
				PriceCandles.pipe(source, sink);
			}
		}
	}

	private IPriceCandleStreamSource createSource(Set<? extends IPtfSource> sources, IPriceInterval interval) {
		return new PriceCandleIntervalStreamSource(new PtfSourcesStreamSource(sources), interval);
	}
}
