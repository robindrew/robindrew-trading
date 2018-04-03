package com.robindrew.trading.backtest;

import static com.robindrew.trading.price.candle.interval.PriceIntervals.MINUTELY;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.position.order.PositionOrder;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.indicator.custom.DirectionIndicator;
import com.robindrew.trading.price.candle.indicator.custom.VolatilityIndicator;
import com.robindrew.trading.strategy.SingleTradeStrategy;
import com.robindrew.trading.trade.TradeDirection;

public class SimpleVolatilityStrategy extends SingleTradeStrategy {

	private static final Logger log = LoggerFactory.getLogger(SimpleVolatilityStrategy.class);

	private final DirectionIndicator direction = new DirectionIndicator("DIR(5 MIN)", MINUTELY, 5);
	private final VolatilityIndicator longTerm = new VolatilityIndicator("VOL(60 MIN)", MINUTELY, 60);
	private final VolatilityIndicator mediumTerm = new VolatilityIndicator("VOL(15 MIN)", MINUTELY, 15);
	private final VolatilityIndicator shortTerm = new VolatilityIndicator("VOL(5 MIN)", MINUTELY, 5);

	public SimpleVolatilityStrategy(ITradingPlatform platform, IInstrument instrument) {
		super("SimpleVolatilityStrategy", platform, instrument);
	}

	@Override
	public void close() {
		direction.close();
		longTerm.close();
		mediumTerm.close();
		shortTerm.close();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		// Populate!
		direction.putNextCandle(candle);
		longTerm.putNextCandle(candle);
		mediumTerm.putNextCandle(candle);
		shortTerm.putNextCandle(candle);

		// Checks
		if (!direction.isAvailable() || !longTerm.isAvailable() || !mediumTerm.isAvailable() || !shortTerm.isAvailable()) {
			return;
		}

		// Wait for the open position to resolve
		if (hasOpenPosition()) {
			checkOpenPosition(candle);
		}

		else {

			if (conditionsAreRight()) {
				log.info("[Trade Opportunity] short=" + shortTerm.getValue() + ", medium=" + mediumTerm.getValue() + ", long=" + longTerm.getValue() + ", date=" + candle.getOpenDate());

				TradeDirection direction = this.direction.getDirection();
				CurrencyCode currency = CurrencyCode.GBP;
				BigDecimal tradeSize = BigDecimal.ONE;
				int stopLossDistance = (int) (longTerm.getValue() * 2);
				int profitLimitDistance = (int) (longTerm.getValue() * 2);

				IPositionOrder order = new PositionOrder(getInstrument(), direction, currency, tradeSize, stopLossDistance, profitLimitDistance);
				openPosition(order);
			}
		}

	}

	private boolean conditionsAreRight() {
		return shortTerm.getValue() >= mediumTerm.getValue() * 2 && mediumTerm.getValue() >= longTerm.getValue() * 2;
	}

}
