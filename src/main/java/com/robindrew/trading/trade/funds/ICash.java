package com.robindrew.trading.trade.funds;

import java.math.BigDecimal;

public interface ICash {

	BigDecimal get();

	boolean isPositive();

	ICash multiply(BigDecimal amount);

	ICash divide(BigDecimal amount);

	ICash add(BigDecimal amount);

	ICash subtract(BigDecimal amount);

	ICash multiply(ICash pounds);

	ICash divide(ICash pounds);

	ICash add(ICash pounds);

	ICash subtract(ICash pounds);

	ICash negate();

}
