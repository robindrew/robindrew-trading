package com.robindrew.trading.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.precision.IPricePrecision;

public abstract class SingleTradeStrategy extends SingleInstrumentStrategy {

	private static final Logger log = LoggerFactory.getLogger(SingleTradeStrategy.class);

	private final AtomicReference<IPosition> openPosition = new AtomicReference<IPosition>();

	protected SingleTradeStrategy(String name, ITradingPlatform platform, IInstrument instrument) {
		super(name, platform, instrument);
	}

	public boolean hasOpenPosition() {
		return openPosition.get() != null;
	}

	public IPosition getOpenPosition() {
		IPosition position = openPosition.get();
		if (position == null) {
			throw new IllegalStateException("No position open");
		}
		return position;
	}

	public void setOpenPosition(IPosition position) {
		if (!openPosition.compareAndSet(null, position)) {
			throw new IllegalStateException("Position already open");
		}
		log.info("Position Opened {}", position);
	}

	public void openPosition(IPositionOrder order) {
		IPosition position = getPlatform().openPosition(order);
		setOpenPosition(position);
	}

	public void checkOpenPosition(IPriceCandle candle) {

		IPricePrecision precision = getPlatform().getPrecision(getInstrument());
		IPosition position = getOpenPosition();

		int high = candle.getHighPrice();
		int low = candle.getLowPrice();

		// Has stop loss triggered?
		int stopLoss = precision.toBigInt(position.getStopLossPrice());
		if (low <= stopLoss && stopLoss <= high) {
			closeOpenPosition(candle.getCloseDate(), position.getStopLossPrice());
			return;
		}

		// Has profit limit triggered?
		int profitLimit = precision.toBigInt(position.getProfitLimitPrice());
		if (low <= profitLimit && profitLimit <= high) {
			closeOpenPosition(candle.getCloseDate(), position.getProfitLimitPrice());
			return;
		}
	}

	public void closeOpenPosition(LocalDateTime closeDate, BigDecimal expectedClosePrice) {
		IPosition position = openPosition.getAndSet(null);
		getPlatform().closePosition(position);
	}

}
