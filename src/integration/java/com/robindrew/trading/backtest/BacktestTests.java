package com.robindrew.trading.backtest;

import static com.robindrew.trading.provider.TradeDataProviderSet.defaultProviders;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instruments;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.provider.TradeDataProviderSet;
import com.robindrew.trading.trade.funds.AccountFunds;
import com.robindrew.trading.trade.funds.Cash;

public class BacktestTests {

	private static final String PCF_DATA_DIR = "C:\\development\\repository\\git\\robindrew2\\robindrew-tradedata\\data\\pcf";
	private static final Logger log = LoggerFactory.getLogger(BacktestTests.class);

	@Test
	public void simpleTest() {

		BacktestTradingPlatform platform = getPlatform();

		IInstrument instrument = Instruments.GBP_USD;
		platform.setPrecision(instrument, new PricePrecision(1, 1, 2));
		BacktestInstrumentPriceStream stream = (BacktestInstrumentPriceStream) platform.getStreamingService().getPriceStream(instrument);
		BacktestInstrumentPriceStreamListener listener = stream.getListener();
		listener.register(new SimpleVolatilityStrategy(platform, instrument));
		listener.run();
	}

	private BacktestTradingPlatform getPlatform() {

		AccountFunds funds = new AccountFunds(new Cash(10000));
		File directory = new File(PCF_DATA_DIR);
		TradeDataProviderSet providers = defaultProviders();

		IPcfSourceManager manager = new PcfFileManager(directory, providers);
		BacktestHistoryService history = new BacktestHistoryService(manager);

		BacktestStreamingService streaming = new BacktestStreamingService();
		BacktestTradingPlatform platform = new BacktestTradingPlatform(funds, history, streaming);

		for (IInstrument instrument : history.getInstruments()) {
			log.info("Registering Instrument: {}", instrument);
			IInstrumentPriceHistory priceHistory = history.getPriceHistory(instrument);
			BacktestInstrumentPriceStreamListener listener = new BacktestInstrumentPriceStreamListener(priceHistory);
			BacktestInstrumentPriceStream stream = new BacktestInstrumentPriceStream(listener);
			streaming.register(stream);
		}

		return platform;
	}

}
