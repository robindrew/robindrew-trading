package com.robindrew.trading.strategy;

import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public abstract class AbstractTradingStrategy implements IPriceCandleStreamSink {

	private final String name;
	private final ITradingPlatform platform;

	protected AbstractTradingStrategy(String name, ITradingPlatform platform) {
		this.name = Check.notEmpty("name", name);
		this.platform = Check.notNull("platform", platform);
	}

	@Override
	public String getName() {
		return name;
	}

	public ITradingPlatform getPlatform() {
		return platform;
	}

}
