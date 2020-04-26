package com.robindrew.trading.platform;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.ITradingInstrument;
import com.robindrew.trading.trade.currency.Currency;

public class TradingPlatformServices<I extends ITradingInstrument> implements ITradingPlatformServices<I> {

	private final ITradingPlatform<I> platform;

	public TradingPlatformServices(ITradingPlatform<I> platform) {
		this.platform = Check.notNull("platform", platform);
	}

	@Override
	public String getAccountId() {
		return platform.getAccountService().getAccountId();
	}

	@Override
	public Currency getBalance() {
		return platform.getAccountService().getBalance();
	}

	@Override
	public ITradingProvider getProvider() {
		return platform.getAccountService().getProvider();
	}

	@Override
	public List<? extends IPosition> getAllPositions() {
		return platform.getPositionService().getAllPositions();
	}

	@Override
	public List<? extends IPosition> getPositions(IInstrument instrument) {
		return platform.getPositionService().getPositions(instrument);
	}

	@Override
	public IClosedPosition closePosition(IPosition position) {
		return platform.getPositionService().closePosition(position);
	}

	@Override
	public Map<IPosition, IClosedPosition> closePositions(Collection<? extends IPosition> positions) {
		return platform.getPositionService().closePositions(positions);
	}

	@Override
	public List<? extends IClosedPosition> closeAllPositions() {
		return platform.getPositionService().closeAllPositions();
	}

	@Override
	public IPosition openPosition(IPositionOrder order) {
		return platform.getPositionService().openPosition(order);
	}

	@Override
	public Set<? extends IInstrument> getInstruments() {
		return platform.getHistoryService().getInstruments();
	}

	@Override
	public IInstrumentPriceHistory getPriceHistory(IInstrument instrument) {
		return platform.getHistoryService().getPriceHistory(instrument);
	}

	@Override
	public boolean isSubscribedInstrument(I instrument) {
		return platform.getStreamingService().isSubscribedInstrument(instrument);
	}

	@Override
	public boolean canStreamPrices(I instrument) {
		return platform.getStreamingService().canStreamPrices(instrument);
	}

	@Override
	public boolean subscribeToPrices(I instrument) {
		return platform.getStreamingService().subscribeToPrices(instrument);
	}

	@Override
	public boolean unsubscribeFromPrices(I instrument) {
		return platform.getStreamingService().unsubscribeFromPrices(instrument);
	}

	@Override
	public Set<I> getSubscribedInstruments() {
		return platform.getStreamingService().getSubscribedInstruments();
	}

	@Override
	public Set<IInstrumentPriceStream<I>> getPriceStreams() {
		return platform.getStreamingService().getPriceStreams();
	}

	@Override
	public IInstrumentPriceStream<I> getPriceStream(I instrument) {
		return platform.getStreamingService().getPriceStream(instrument);
	}

	@Override
	public void close() {
		platform.getStreamingService().close();
	}

}
