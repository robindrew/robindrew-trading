package com.robindrew.trading.platform.positions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.precision.IPricePrecision;

public interface IPositionService {

	IPricePrecision getPrecision(IInstrument instrument);

	List<? extends IPosition> getAllPositions();

	List<? extends IPosition> getPositions(IInstrument instrument);

	IClosedPosition closePosition(IPosition position);

	Map<IPosition, IClosedPosition> closePositions(Collection<? extends IPosition> positions);

	IPosition openPosition(IPositionOrder order);

}
