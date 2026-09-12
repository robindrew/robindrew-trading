package com.robindrew.trading.platform.positions;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IPositionService extends ITradingService {

    List<? extends IPosition> getAllPositions();

    List<? extends IPosition> getPositions(IInstrument instrument);

    IClosedPosition closePosition(IPosition position);

    Map<IPosition, IClosedPosition> closePositions(Collection<? extends IPosition> positions);

    List<? extends IClosedPosition> closeAllPositions();

    IPosition openPosition(IPositionOrder order);
}
