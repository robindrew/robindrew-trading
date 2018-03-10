package com.robindrew.trading.price.tick;

import java.math.BigDecimal;

public interface IPriceTick {

	long getTimestamp();
	
	BigDecimal getBid();
	
	BigDecimal getAsk();
	
	BigDecimal getMid();

}
