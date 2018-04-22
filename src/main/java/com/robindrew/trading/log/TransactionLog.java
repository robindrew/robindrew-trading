package com.robindrew.trading.log;

import static com.google.common.base.Charsets.UTF_8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.concurrent.LoopingRunnableThread;
import com.robindrew.common.date.Dates;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

/**
 * The Transaction Log is intended as a file-backed log for recording details of every transaction you execute.
 * Specifically the orders and trades you place and close or cancel, and the response to each request.
 */
public class TransactionLog extends AbstractTransactionLog implements ITransactionLog, Runnable {

	private static final Logger log = LoggerFactory.getLogger(TransactionLog.class);

	private final File directory;
	private final BlockingDeque<Entry> entryQueue = new LinkedBlockingDeque<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,S");

	public TransactionLog(File directory) {
		this.directory = Check.existsDirectory("directory", directory);
	}

	public LoopingRunnableThread start() {
		LoopingRunnableThread thread = new LoopingRunnableThread("TransactionLog", this);
		thread.start();
		return thread;
	}

	@Override
	public void run() {
		try {
			List<Entry> list = new ArrayList<>();
			entryQueue.drainTo(list);
			if (!list.isEmpty()) {
				writeEntries(list);
			}
		} catch (Exception e) {
			log.warn("Exception while writing entries", e);
		}
	}

	private void writeEntries(List<Entry> list) throws IOException {
		File currentFile = null;
		Writer currentWriter = null;
		for (Entry entry : list) {

			// Get the file
			File entryFile = getFile(entry);

			// Create the output stream
			if (currentFile == null || !currentFile.equals(entryFile)) {
				close(currentWriter);
				currentFile = entryFile;
				if (!currentFile.exists()) {
					log.info("[New Log File] {}", currentFile);
				}
				currentWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentFile, true), UTF_8));
			}

			// Write the entry
			writeEntry(entry, currentWriter);
		}
		if (currentWriter != null) {
			close(currentWriter);
		}
	}

	private void writeEntry(Entry entry, Writer writer) throws IOException {
		StringBuilder line = new StringBuilder();
		line.append(formatDate(entry.getTimestamp()));
		line.append(' ');
		line.append(entry.getHeader());
		line.append('\n');

		// Content
		Object content = entry.getContent();
		if (content instanceof CharSequence) {
			line.append(content);
		} else {
			line.append(Strings.json(content, true));
		}

		line.append('\n');
		writer.write(Strings.chars(line));
	}

	private String formatDate(long timestamp) {
		LocalDateTime date = Dates.toLocalDateTime(timestamp);
		return date.format(formatter);
	}

	private void close(Writer writer) throws IOException {
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	private File getFile(Entry entry) {
		LocalDate date = Dates.toLocalDateTime(entry.getTimestamp()).toLocalDate();
		return new File(directory, "transaction." + date + ".log");
	}

	@Override
	protected void log(Entry entry) {
		entryQueue.add(entry);
	}

}
