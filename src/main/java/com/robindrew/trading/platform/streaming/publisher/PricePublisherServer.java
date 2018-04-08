package com.robindrew.trading.platform.streaming.publisher;

import com.robindrew.common.io.data.server.DataServer;

public class PricePublisherServer extends DataServer implements IPricePublisherServer, PricePublisherServerMBean {

	public PricePublisherServer(String name, int port) {
		super(name, port, new PricePublisherHandler());
	}

	public PricePublisherServer(int port) {
		this("PricePublisherServer", port);
	}

	@Override
	public PricePublisherHandler getHandler() {
		return (PricePublisherHandler) super.getHandler();
	}

}
