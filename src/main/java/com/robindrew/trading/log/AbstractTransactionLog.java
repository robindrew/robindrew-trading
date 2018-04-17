package com.robindrew.trading.log;

import com.robindrew.common.util.Check;

public abstract class AbstractTransactionLog implements ITransactionLog {

	@Override
	public void log(Object content) {
		log(content.getClass().getSimpleName(), content);
	}

	@Override
	public void log(String header, Object content) {
		long timestamp = System.currentTimeMillis();

		Check.notEmpty("header", header);
		Check.notNull("content", content);

		Entry entry = new Entry(timestamp, header, content);
		log(entry);
	}

	protected abstract void log(Entry entry);

	public static class Entry {

		private final long timestamp;
		private final String header;
		private final Object content;

		public Entry(long timestamp, String header, Object content) {
			this.timestamp = timestamp;
			this.header = header;
			this.content = content;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public String getHeader() {
			return header;
		}

		public Object getContent() {
			return content;
		}
	}

}
