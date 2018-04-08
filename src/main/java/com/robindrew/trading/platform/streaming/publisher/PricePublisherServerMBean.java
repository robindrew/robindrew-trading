package com.robindrew.trading.platform.streaming.publisher;

public interface PricePublisherServerMBean {

	String getName();

	int getPort();
	
	long getConnectionCount();

}
