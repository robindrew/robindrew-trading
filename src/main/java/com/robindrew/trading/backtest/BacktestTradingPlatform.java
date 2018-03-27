package com.robindrew.trading.backtest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.TradingPlatform;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.platform.streaming.latest.IStreamingPrice;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.PositionBuilder;
import com.robindrew.trading.position.closed.ClosedPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.trade.funds.AccountFunds;
import com.robindrew.trading.trade.funds.Cash;

public class BacktestTradingPlatform extends TradingPlatform {

	private static final Logger log = LoggerFactory.getLogger(BacktestTradingPlatform.class);

	private final AccountFunds funds;
	private final BacktestHistoryService history;
	private final BacktestStreamingService streaming;
	private final Map<IInstrument, IPricePrecision> precisionMap = new ConcurrentHashMap<>();

	private final AtomicLong nextId = new AtomicLong(0);
	private final Set<IPosition> openPositions = new CopyOnWriteArraySet<>();
	private final List<IClosedPosition> closedPositions = new CopyOnWriteArrayList<>();

	public BacktestTradingPlatform(AccountFunds funds, BacktestHistoryService history, BacktestStreamingService streaming) {
		this.funds = Check.notNull("funds", funds);
		this.history = Check.notNull("history", history);
		this.streaming = Check.notNull("streaming", streaming);
	}

	protected String getNextId() {
		return "POS#" + nextId.incrementAndGet();
	}

	@Override
	public IHistoryService getHistoryService() {
		return history;
	}

	@Override
	public IStreamingService getStreamingService() {
		return streaming;
	}

	@Override
	public IPricePrecision getPrecision(IInstrument instrument) {
		Check.notNull("instrument", instrument);
		IPricePrecision precision = precisionMap.get(instrument);
		if (precision == null) {
			throw new IllegalArgumentException("precision not configured for instrument: " + instrument);
		}
		return precision;
	}

	public void setPrecision(IInstrument instrument, IPricePrecision precision) {
		Check.notNull("instrument", instrument);
		Check.notNull("precision", precision);
		precisionMap.put(instrument, precision);
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

		IInstrument instrument = position.getInstrument();
		IPricePrecision precision = getPrecision(instrument);

		BacktestInstrumentPriceStream stream = getPriceStream(instrument);
		IStreamingPrice price = stream.getPrice();

		IPriceCandle latest = price.getSnapshot().getLatest();
		BigDecimal closePrice = precision.toBigDecimal(latest.getClosePrice());
		IClosedPosition closed = new ClosedPosition(position, latest.getCloseDate(), closePrice);
		if (closed.isProfit()) {
			funds.addFunds(new Cash(closed.getProfit(), true));
			log.info("Profit: {} ({})", closed.getProfit(), position);
		} else {
			funds.subtractFunds(new Cash(closed.getLoss(), true));
			log.info("Loss: {} ({})", closed.getLoss(), position);
		}
		log.info("Funds: {}", funds);

		closedPositions.add(closed);
		return closed;
	}

	@Override
	public AccountFunds getAvailableFunds() {
		throw new UnsupportedOperationException();
	}

	protected BacktestInstrumentPriceStream getPriceStream(IInstrument instrument) {
		return (BacktestInstrumentPriceStream) getStreamingService().getPriceStream(instrument);
	}

	@Override
	public IPosition openPosition(IPositionOrder order) {

		IInstrument instrument = order.getInstrument();
		IPricePrecision precision = getPrecision(instrument);

		BacktestInstrumentPriceStream stream = getPriceStream(instrument);
		IStreamingPrice price = stream.getPrice();

		String id = getNextId();
		IPriceCandle latest = price.getSnapshot().getLatest();
		BigDecimal openPrice = precision.toBigDecimal(latest.getClosePrice());

		PositionBuilder builder = new PositionBuilder();
		builder.setId(id);
		builder.setInstrument(order.getInstrument());
		builder.setDirection(order.getDirection());
		builder.setOpenDate(latest.getCloseDate());
		builder.setCurrency(order.getTradeCurrency());
		builder.setOpenPrice(openPrice);
		builder.setTradeSize(order.getTradeSize());
		if (order.getDirection().isBuy()) {
			builder.setProfitLimitPrice(openPrice.add(new BigDecimal(order.getProfitLimitDistance())));
			builder.setStopLossPrice(openPrice.subtract(new BigDecimal(order.getStopLossDistance())));
		} else {
			builder.setProfitLimitPrice(openPrice.subtract(new BigDecimal(order.getProfitLimitDistance())));
			builder.setStopLossPrice(openPrice.add(new BigDecimal(order.getStopLossDistance())));
		}
		IPosition position = builder.build();

		openPositions.add(position);
		return position;
	}

}
