package com.robindrew.trading.platform;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.trade.funds.AccountFunds;

public interface ITradingPlatform {

	IHistoryService getHistoryService();

	IStreamingService getStreamingService();

	IPricePrecision getPrecision(IInstrument instrument);

	List<IPosition> getAllPositions();

	List<IPosition> getPositions(IInstrument instrument);

	IClosedPosition closePosition(IPosition position);

	Map<IPosition, IClosedPosition> closePositions(Collection<? extends IPosition> positions);

	AccountFunds getAvailableFunds();

	IPosition openPosition(IPositionOrder order);
}
