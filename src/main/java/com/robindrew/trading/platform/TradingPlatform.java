package com.robindrew.trading.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;

public abstract class TradingPlatform implements ITradingPlatform {

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
}
