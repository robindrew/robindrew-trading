package com.robindrew.trading.position;

import static com.robindrew.common.locale.CurrencyCode.GBP;
import static com.robindrew.trading.Instruments.GBP_USD;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.robindrew.trading.trade.TradeDirection;

public class PositionBuilderTest {

	@Test
	public void testBuildBuyPosition() {

		PositionBuilder builder = new PositionBuilder();
		builder.id("id");
		builder.instrument(GBP_USD);
		builder.currency(GBP);
		builder.direction(TradeDirection.BUY);
		builder.openDate(LocalDateTime.now());
		builder.openPrice(new BigDecimal("1.230"));
		builder.stopLossPrice(new BigDecimal("1.211"));
		builder.profitLimitPrice(new BigDecimal("1.238"));
		builder.tradeSize(BigDecimal.ONE);
		builder.build();

	}

	@Test
	public void testBuildSellPosition() {

		PositionBuilder builder = new PositionBuilder();
		builder.id("id");
		builder.instrument(GBP_USD);
		builder.currency(GBP);
		builder.direction(TradeDirection.SELL);
		builder.openDate(LocalDateTime.now());
		builder.openPrice(new BigDecimal("0.887"));
		builder.stopLossPrice(new BigDecimal("0.9"));
		builder.profitLimitPrice(new BigDecimal("0.803"));
		builder.tradeSize(BigDecimal.ONE);
		builder.build();

	}

}
