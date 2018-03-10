package com.robindrew.trading.platform.streaming;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;

public class StreamingSubscriptionConfig implements IStreamingSubscriptionConfig {

	private final IInstrument instrument;

	public StreamingSubscriptionConfig(IInstrument instrument) {
		this.instrument = Check.notNull("instrument", instrument);
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

}
