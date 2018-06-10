package com.robindrew.trading.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.concurrent.LoopingRunnableThread;

public abstract class WriteBehindTransactionLog extends AbstractTransactionLog implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(WriteBehindTransactionLog.class);

	private final BlockingDeque<Entry> entryQueue = new LinkedBlockingDeque<>();

	public LoopingRunnableThread start(String threadName) {
		LoopingRunnableThread thread = new LoopingRunnableThread(threadName, this);
		thread.start();
		return thread;
	}

	@Override
	public void run() {
		List<Entry> list = new ArrayList<>();
		entryQueue.drainTo(list);
		if (!list.isEmpty()) {
			try {
				writeEntries(list);
			} catch (Exception e) {
				log.warn("Exception while writing entries", e);
			}
		}
	}

	protected abstract void writeEntries(List<Entry> list) throws Exception;

	@Override
	protected void log(Entry entry) {
		entryQueue.add(entry);
	}

}
