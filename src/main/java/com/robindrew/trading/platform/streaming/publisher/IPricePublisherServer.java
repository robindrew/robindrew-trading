package com.robindrew.trading.platform.streaming.publisher;

import com.robindrew.common.io.data.server.IDataServer;

public interface IPricePublisherServer extends IDataServer {

	@Override
	PricePublisherHandler getHandler() ;

}
