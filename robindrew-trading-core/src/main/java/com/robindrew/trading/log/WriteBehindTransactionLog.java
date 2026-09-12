package com.robindrew.trading.log;

import com.robindrew.common.concurrent.LoopingRunnableThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class WriteBehindTransactionLog extends AbstractTransactionLog implements Runnable {

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
