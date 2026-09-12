package com.robindrew.trading.backtest.context;

import static com.robindrew.common.locale.CurrencyCode.GBP;
import static com.robindrew.common.util.Check.notNull;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.backtest.platform.BacktestTradingPlatform;
import com.robindrew.trading.backtest.platform.account.BacktestAccountService;
import com.robindrew.trading.backtest.platform.history.BacktestHistoryService;
import com.robindrew.trading.backtest.platform.position.BacktestPositionService;
import com.robindrew.trading.backtest.platform.streaming.BacktestStreamingService;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.decimal.IDecimal;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.trade.currency.Currency;
import java.io.File;

public class BacktestContextBuilder {

    private File dataDirectory;
    private ITradingProvider provider;
    private IInstrument instrument;
    private String accountId = "BacktestAccount001";
    private Currency balance = new Currency(GBP, 10000);
    private IDecimal spread;

    public BacktestContextBuilder setDataDirectory(File directory) {
        this.dataDirectory = Check.existsDirectory("directory", directory);
        return this;
    }

    public BacktestContextBuilder setSpread(IDecimal spread) {
        this.spread = Check.notNull("spread", spread);
        return this;
    }

    public BacktestContextBuilder setDataDirectory(String rootDirectory) {
        return setDataDirectory(new File(rootDirectory));
    }

    public BacktestContextBuilder setProvider(ITradingProvider provider) {
        this.provider = notNull("provider", provider);
        return this;
    }

    public BacktestContextBuilder setInstrument(IBacktestInstrument instrument) {
        this.instrument = notNull("instrument", instrument);
        return this;
    }

    public BacktestContext build() {
        if (dataDirectory == null) {
            throw new IllegalStateException("rootDirectory not set");
        }
        if (provider == null) {
            throw new IllegalStateException("provider not set");
        }
        if (instrument == null) {
            throw new IllegalStateException("instrument not set");
        }
        if (spread == null) {
            throw new IllegalStateException("spread not set");
        }

        PcfFileManager manager = new PcfFileManager(dataDirectory);
        IPcfSourceProviderManager providerManager = manager.getProvider(provider);
        IPcfSourceSet sourceSet = providerManager.getSourceSet(instrument);
        BacktestHistoryService history = new BacktestHistoryService(providerManager);

        BacktestAccountService account = new BacktestAccountService(provider, accountId, balance);
        BacktestStreamingService streaming = new BacktestStreamingService(history);
        BacktestPositionService position = new BacktestPositionService(provider, balance, streaming, spread);

        BacktestTradingPlatform platform = new BacktestTradingPlatform(account, history, streaming, position);

        return new BacktestContext(platform, sourceSet);
    }
}
