package com.robindrew.trading.platform.positions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.trade.funds.AccountFunds;

public class UnavailablePositionService implements IPositionService {

	@Override
	public IPricePrecision getPrecision(IInstrument instrument) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<? extends IPosition> getAllPositions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<? extends IPosition> getPositions(IInstrument instrument) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IClosedPosition closePosition(IPosition position) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<IPosition, IClosedPosition> closePositions(Collection<? extends IPosition> positions) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AccountFunds getAvailableFunds() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPosition openPosition(IPositionOrder order) {
		throw new UnsupportedOperationException();
	}

}
