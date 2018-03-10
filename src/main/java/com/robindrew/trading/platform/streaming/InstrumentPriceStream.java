package com.robindrew.trading.platform.streaming;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.ILatestPrice;

public abstract class InstrumentPriceStream implements IInstrumentPriceStream {

	private final IInstrument instrument;
	private final ILatestPrice latest;

	protected InstrumentPriceStream(IInstrument instrument, ILatestPrice latest) {
		this.instrument = Check.notNull("instrument", instrument);
		this.latest = Check.notNull("latest", latest);
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public ILatestPrice getLatestPrice() {
		return latest;
	}

}
