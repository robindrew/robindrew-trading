package com.robindrew.trading.price.candle.format.pcf.source.file;

import static com.robindrew.trading.price.candle.interval.TimeUnitInterval.ONE_MINUTE;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.robindrew.common.properties.map.type.FileProperty;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.generator.PriceCandleGenerator;
import com.robindrew.trading.price.candle.interval.IPriceInterval;

public class PcfFileTest {

	@Test
	public void writeAndReadFile() throws IOException {
		File tempDir = new FileProperty("PcfFileTest.temp.dir").existsDirectory().get();

		// Generate some candles
		LocalDateTime fromDate = LocalDateTime.of(2018, 01, 01, 0, 0);
		IPriceInterval interval = ONE_MINUTE;
		PriceCandleGenerator generator = new PriceCandleGenerator(interval);
		List<IPriceCandle> before = generator.generateCandles(1440, fromDate);

		File tempFile = new File(tempDir, PcfFormat.getFilename(fromDate.toLocalDate()));
		tempFile.deleteOnExit();

		PcfFile pcf = new PcfFile(tempFile);
		pcf.write(before);

		List<IPriceCandle> after = pcf.read();
		Assert.assertEquals(before, after);
	}

}
