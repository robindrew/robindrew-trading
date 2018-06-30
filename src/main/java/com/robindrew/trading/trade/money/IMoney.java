package com.robindrew.trading.trade.money;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.price.decimal.IDecimal;

public interface IMoney {

	CurrencyCode getCurrency();

	IDecimal getValue();

	void setValue(IDecimal value);

	void add(BigDecimal value);

	void subtract(BigDecimal value);

	void multiply(BigDecimal value);

	void add(IDecimal value);

	void subtract(IDecimal value);

	void multiply(IDecimal value);

	void add(IMoney value);

	void subtract(IMoney value);

	void multiply(IMoney value);

}
