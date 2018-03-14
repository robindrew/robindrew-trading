package com.robindrew.trading.provider.histdata.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.io.ByteStreams;
import com.robindrew.common.lang.Args;
import com.robindrew.common.util.Check;

public class HistDataDownloader {

	private static final Logger log = LoggerFactory.getLogger(HistDataDownloader.class);

	public static final String DEFAULT_HOST = "ftpsite.histdata.com";
	public static final int DEFAULT_PORT = 21;

	private static final String TICK = "_T_";
	private static final String ASCII_FILE_PREFIX = "DAT_ASCII_";

	public static void main(String[] array) throws Exception {
		Args args = new Args(array);

		String username = args.get("-u");
		String password = args.get("-p");
		String directory = args.get("-d");

		new HistDataDownloader(username, password, directory).downloadAll();
	}

	private final String rootDirectory;
	private final String username;
	private final String password;

	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;

	public HistDataDownloader(String username, String password, String directory) {
		this.username = Check.notNull("username", username);
		this.password = Check.notNull("password", password);
		this.rootDirectory = Check.notNull("directory", directory);
	}

	public void downloadAll() throws Exception {

		FTPClient client = new FTPClient();
		client.connect(host, port);
		client.login(username, password);

		// Force binary transfer
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);

		for (FTPFile directory : client.listDirectories()) {
			String year = directory.getName();
			downloadYear(client, directory, year);
		}
	}

	private void downloadYear(FTPClient client, FTPFile directory, String year) throws Exception {
		String path = year;
		for (FTPFile dir : client.listDirectories(path)) {
			String instrument = dir.getName();
			downloadInstrument(client, dir, year, instrument);
		}
	}

	private void downloadInstrument(FTPClient client, FTPFile directory, String year, String instrument) throws Exception {
		String path = year + "/" + instrument;
		for (FTPFile file : client.listFiles(path)) {
			String filename = file.getName();
			if (filename.startsWith(ASCII_FILE_PREFIX + instrument + TICK)) {

				// Local File
				File localFile = new File(rootDirectory + "/" + instrument + "/" + filename);
				long localFileSize = localFile.length();
				long remoteFileSize = file.getSize();
				if (localFile.exists() && localFileSize == remoteFileSize) {
					log.info("Skipping {} (file already downloaded)", filename);
					continue;
				}
				localFile.delete();

				// Remote File
				String remoteFilename = path + "/" + filename;
				try (InputStream remoteFileInput = client.retrieveFileStream(remoteFilename)) {
					if (remoteFileInput == null) {
						continue;
					}

					log.info("Downloading {} to {}", remoteFilename, localFile.getAbsolutePath());
					Stopwatch timer = Stopwatch.createStarted();
					localFile.getParentFile().mkdirs();
					try (FileOutputStream localFileOutput = new FileOutputStream(localFile)) {
						ByteStreams.copy(remoteFileInput, localFileOutput);
					}
					timer.stop();
					log.info("Downloaded {} in {}", filename, timer);
				}
				client.completePendingCommand();
			}
		}
	}
}
