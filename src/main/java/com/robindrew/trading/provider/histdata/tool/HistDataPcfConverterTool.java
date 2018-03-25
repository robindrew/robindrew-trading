package com.robindrew.trading.provider.histdata.tool;

import static com.robindrew.trading.provider.TradeDataProvider.HISTDATA;
import static com.robindrew.trading.provider.histdata.line.HistDataFormat.TICK;
import static java.util.concurrent.TimeUnit.HOURS;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.robindrew.common.io.Files;
import com.robindrew.common.lang.Args;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.candle.line.filter.ILineFilter;
import com.robindrew.trading.price.candle.line.parser.IPriceCandleLineParser;
import com.robindrew.trading.provider.TradeDataProvider;
import com.robindrew.trading.provider.histdata.HistDataInstrument;
import com.robindrew.trading.provider.histdata.HistDataPcfLineConverter;
import com.robindrew.trading.provider.histdata.line.HistDataFormat;
import com.robindrew.trading.provider.histdata.line.HistDataLineFilter;
import com.robindrew.trading.provider.histdata.line.HistDataM1LineParser;
import com.robindrew.trading.provider.histdata.line.HistDataTickLineParser;

public class HistDataPcfConverterTool implements Runnable {

	public static void main(String[] array) {
		Args args = new Args(array);

		File inputDir = args.getDirectory("-i", true);
		File outputDir = args.getDirectory("-o", true);
		HistDataFormat format = args.getEnum("-f", HistDataFormat.class, TICK);
		TradeDataProvider provider = args.getEnum("-p", TradeDataProvider.class, HISTDATA);

		IPcfSourceManager manager = new PcfFileManager(outputDir, provider);

		new HistDataPcfConverterTool(inputDir, manager, format).run();;
	}

	private final File inputDir;
	private final IPcfSourceManager manager;
	private final HistDataFormat format;
	private final ExecutorService executor = Executors.newFixedThreadPool(5);

	public HistDataPcfConverterTool(File inputDir, IPcfSourceManager manager, HistDataFormat format) {
		this.inputDir = Check.existsDirectory("inputDir", inputDir);
		this.manager = Check.notNull("manager", manager);
		this.format = Check.notNull("format", format);
	}

	@Override
	public void run() {
		convertFiles();
	}

	private void convertFiles() {
		for (File instrumentDir : Files.listContents(inputDir)) {
			executor.execute(() -> convertDirectory(instrumentDir));
		}
		Threads.shutdownService(executor, 1, HOURS);
	}

	private void convertDirectory(File fromDirectory) {

		HistDataInstrument instrument = HistDataInstrument.valueOf(fromDirectory.getName());
		IPriceCandleLineParser parser = createParser(format, instrument);
		ILineFilter filter = new HistDataLineFilter();
		HistDataPcfLineConverter converter = new HistDataPcfLineConverter(manager, parser, filter);
		converter.convert(instrument, fromDirectory);
	}

	private IPriceCandleLineParser createParser(HistDataFormat format2, HistDataInstrument instrument) {
		switch (format) {
			case M1:
				return new HistDataM1LineParser(instrument);
			case TICK:
				return new HistDataTickLineParser(instrument);
			default:
				throw new IllegalArgumentException("Format not supported: " + format);
		}
	}
}
