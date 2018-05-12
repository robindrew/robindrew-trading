package com.robindrew.trading.platform.positions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.AbstractTradingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.provider.ITradingProvider;

public class AbstractPositionService extends AbstractTradingService implements IPositionService {

	protected AbstractPositionService(ITradingProvider provider) {
		super(provider);
	}

	@Override
	public Map<IPosition, IClosedPosition> closePositions(Collection<? extends IPosition> positions) {
		Map<IPosition, IClosedPosition> map = new LinkedHashMap<>();
		for (IPosition position : positions) {
			IClosedPosition closed = closePosition(position);
			map.put(position, closed);
		}
		return map;
	}

	@Override
	public List<IPosition> getPositions(IInstrument instrument) {
		List<IPosition> positions = new ArrayList<>();
		for (IPosition position : getAllPositions()) {
			if (position.getInstrument().equals(instrument)) {
				positions.add(position);
			}
		}
		return positions;
	}

	@Override
	public IPricePrecision getPrecision(IInstrument instrument) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<? extends IPosition> getAllPositions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IClosedPosition closePosition(IPosition position) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPosition openPosition(IPositionOrder order) {
		throw new UnsupportedOperationException();
	}

}
