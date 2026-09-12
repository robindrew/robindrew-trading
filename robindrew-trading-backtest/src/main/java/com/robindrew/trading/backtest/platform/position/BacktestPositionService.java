package com.robindrew.trading.backtest.platform.position;

import static com.robindrew.common.util.Check.notNull;

import com.google.common.collect.ImmutableList;
import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.backtest.platform.streaming.BacktestInstrumentPriceStream;
import com.robindrew.trading.backtest.platform.streaming.BacktestStreamingService;
import com.robindrew.trading.platform.positions.AbstractPositionService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.PositionBuilder;
import com.robindrew.trading.position.closed.ClosedPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.streaming.IStreamingCandlePrice;
import com.robindrew.trading.price.decimal.IDecimal;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.trade.currency.Currency;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BacktestPositionService extends AbstractPositionService {

    private volatile Currency balance;
    private final AtomicLong nextId = new AtomicLong(0);
    private final Set<IPosition> openPositions = new CopyOnWriteArraySet<>();
    private final List<IClosedPosition> closedPositions = new CopyOnWriteArrayList<>();
    private final BacktestStreamingService streaming;
    private final IDecimal spread;

    public BacktestPositionService(
            ITradingProvider provider, Currency balance, BacktestStreamingService streaming, IDecimal spread) {
        super(provider);
        this.balance = notNull("balance", balance);
        this.streaming = notNull("streaming", streaming);
        this.spread = notNull("spread", spread);
    }

    protected String getNextId() {
        return "POS#" + nextId.incrementAndGet();
    }

    public IDecimal getSpread() {
        return spread;
    }

    @Override
    public List<IPosition> getAllPositions() {
        return ImmutableList.copyOf(openPositions);
    }

    @Override
    public IClosedPosition closePosition(IPosition position) {
        if (!openPositions.remove(position)) {
            throw new IllegalArgumentException("Position does not exist: " + position + " in " + openPositions);
        }

        IBacktestInstrument instrument = (IBacktestInstrument) position.getInstrument();
        IPricePrecision precision = instrument.getPrecision();

        BacktestInstrumentPriceStream stream = getPriceStream(instrument);
        IStreamingCandlePrice price = stream.getPrice();

        IPriceCandle latest = price.getSnapshot().getLatest();
        BigDecimal closePrice = precision.toBigDecimal(latest.getMidClosePrice());
        IClosedPosition closed = new ClosedPosition(position, latest.getCloseDate(), closePrice);
        if (closed.isProfit()) {
            balance = balance.add(closed.getProfit());
            log.info("Profit: {} ({})", closed.getProfit(), position);
        } else {
            balance = balance.subtract(closed.getLoss());
            log.info("Loss: {} ({})", closed.getLoss(), position);
        }
        log.info("Funds: {}", balance);

        closedPositions.add(closed);
        return closed;
    }

    protected BacktestInstrumentPriceStream getPriceStream(IBacktestInstrument instrument) {
        return (BacktestInstrumentPriceStream) streaming.getPriceStream(instrument);
    }

    @Override
    public IPosition openPosition(IPositionOrder order) {

        IBacktestInstrument instrument = (IBacktestInstrument) order.getInstrument();
        IPricePrecision precision = instrument.getPrecision();

        BacktestInstrumentPriceStream stream = getPriceStream(instrument);
        IStreamingCandlePrice price = stream.getPrice();

        String id = getNextId();
        IPriceCandle latest = price.getSnapshot().getLatest();
        BigDecimal openPrice = precision.toBigDecimal(latest.getMidClosePrice());

        PositionBuilder builder = new PositionBuilder();
        builder.id(id);
        builder.instrument(order.getInstrument());
        builder.direction(order.getDirection());
        builder.openDate(latest.getCloseDate());
        builder.currency(order.getTradeCurrency());
        builder.openPrice(openPrice);
        builder.tradeSize(order.getTradeSize());
        if (order.getDirection().isBuy()) {
            builder.profitLimitPrice(openPrice.add(order.getProfitLimitDistance()));
            builder.stopLossPrice(openPrice.subtract(order.getStopLossDistance()));
        } else {
            builder.profitLimitPrice(openPrice.subtract(order.getProfitLimitDistance()));
            builder.stopLossPrice(openPrice.add(order.getStopLossDistance()));
        }
        IPosition position = builder.build();

        openPositions.add(position);
        return position;
    }
}
