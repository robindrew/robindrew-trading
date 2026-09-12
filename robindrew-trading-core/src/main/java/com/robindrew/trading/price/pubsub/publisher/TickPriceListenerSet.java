package com.robindrew.trading.price.pubsub.publisher;

import com.robindrew.trading.price.pubsub.ITickPriceEvent;
import com.robindrew.trading.price.pubsub.ITickPriceListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class TickPriceListenerSet implements ITickPriceListener {

    private final Set<ITickPriceListener> listenerSet = new CopyOnWriteArraySet<>();

    @Override
    public void nextEvent(ITickPriceEvent event) {
        for (ITickPriceListener listener : listenerSet) {
            listener.nextEvent(event);
        }
    }
}
