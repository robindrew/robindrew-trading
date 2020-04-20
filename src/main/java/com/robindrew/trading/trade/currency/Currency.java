package com.robindrew.trading.trade.currency;

import static com.robindrew.common.util.Check.notNull;

import java.math.BigDecimal;

import com.robindrew.common.concurrent.Immutable;
import com.robindrew.common.locale.CurrencyCode;

@Immutable
public class Currency {

	private final CurrencyCode code;
	private final BigDecimal value;

	public Currency(CurrencyCode code, BigDecimal value) {
		this.code = notNull("code", code);
		this.value = notNull("value", value);
	}

	public Currency(CurrencyCode currency, long value) {
		this(currency, new BigDecimal(value));
	}

	public CurrencyCode getCode() {
		return code;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Currency add(BigDecimal addValue) {
		return new Currency(code, getValue().add(addValue));
	}

	public Currency subtract(BigDecimal subtractValue) {
		return new Currency(code, getValue().subtract(subtractValue));
	}
}
