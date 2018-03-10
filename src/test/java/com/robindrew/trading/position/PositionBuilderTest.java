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
		builder.setId("id");
		builder.setInstrument(GBP_USD);
		builder.setCurrency(GBP);
		builder.setDirection(TradeDirection.BUY);
		builder.setOpenDate(LocalDateTime.now());
		builder.setOpenPrice(new BigDecimal("1.230"));
		builder.setStopLossPrice(new BigDecimal("1.211"));
		builder.setProfitLimitPrice(new BigDecimal("1.238"));
		builder.setTradeSize(BigDecimal.ONE);
		builder.build();

	}

	@Test
	public void testBuildSellPosition() {

		PositionBuilder builder = new PositionBuilder();
		builder.setId("id");
		builder.setInstrument(GBP_USD);
		builder.setCurrency(GBP);
		builder.setDirection(TradeDirection.SELL);
		builder.setOpenDate(LocalDateTime.now());
		builder.setOpenPrice(new BigDecimal("0.887"));
		builder.setStopLossPrice(new BigDecimal("0.9"));
		builder.setProfitLimitPrice(new BigDecimal("0.803"));
		builder.setTradeSize(BigDecimal.ONE);
		builder.build();

	}

}
