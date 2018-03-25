package com.robindrew.trading.backtest.analysis.volatility;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.Test;

import com.robindrew.common.util.SystemProperties;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instruments;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.candle.io.stream.PriceCandleStreamPipe;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleLoggedStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamSourceBuilder;
import com.robindrew.trading.provider.TradeDataProvider;

public class VolatilityAnalysisTest {

	@Test
	public void testFromPcfFiles() {

		File directory = new File(SystemProperties.get("directory", false));
		TradeDataProvider provider = TradeDataProvider.valueOf(SystemProperties.get("provider", false));
		IInstrument instrument = Instruments.valueOf(SystemProperties.get("instrument", false));

		IPcfSourceManager manager = new PcfFileManager(directory, provider);
		try (VolatilityAnalysis analysis = new VolatilityAnalysis(instrument)) {

			IPcfSourceSet sourceSet = manager.getSourceSet(instrument);
			LocalDateTime from = LocalDateTime.of(2010, 02, 01, 00, 00);
			LocalDateTime to = LocalDateTime.of(2018, 02, 28, 23, 59);
			Set<? extends IPcfSource> sources = sourceSet.getSources(from, to);
			PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
			builder.setPcfSources(sources);
			IPriceCandleStreamSource stream = builder.get();
			stream = new PriceCandleLoggedStreamSource(stream, 100000);

			// Pipe the candles from the stream to the analysis
			new PriceCandleStreamPipe(stream, analysis).pipe();

		}
	}

}
