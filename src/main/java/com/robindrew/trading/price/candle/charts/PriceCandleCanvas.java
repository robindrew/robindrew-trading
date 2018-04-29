package com.robindrew.trading.price.candle.charts;

import static com.robindrew.common.text.Strings.bytes;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.robindrew.common.image.Images;
import com.robindrew.trading.Instruments;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceManager;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceSet;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSourcesStreamSource;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileManager;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.price.decimal.Decimals;
import com.robindrew.trading.provider.TradeDataProvider;

public class PriceCandleCanvas {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleCanvas.class);

	public static void main(String[] args) throws Throwable {

		String directory = "C:\\development\\data\\converted";
		IPtfSourceManager manager = new PtfFileManager(new File(directory), TradeDataProvider.FXCM);
		IPtfSourceSet set = manager.getSourceSet(Instruments.USD_JPY);

		LocalDateTime from = LocalDateTime.of(2017, 1, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2017, 12, 31, 23, 59);
		Set<? extends IPtfSource> sources = set.getSources(from, to);

		IPriceInterval interval = PriceIntervals.daily(2);
		IPriceCandleStreamSource source = new PtfSourcesStreamSource(sources);
		source = new PriceCandleIntervalStreamSource(source, interval);
		List<IPriceCandle> candles = PriceCandles.drainToList(source);

		int width = 900;
		int height = 600;

		PriceCandleCanvas canvas = new PriceCandleCanvas(width, height);
		canvas.renderCandles(candles, interval);
		canvas.writeAsPng("c:/temp/pcg.png");
	}

	// Immutable
	private final int width;
	private final int height;
	private final BufferedImage image;
	private final Graphics2D graphics;

	// Mutable
	private int borderLeft;
	private int borderRight;
	private int borderTop;
	private int borderBottom;
	private int candleSpacing = 1;
	private int xAxisOffset = 40;
	private int yAxisOffset = 20;
	private Color background = Color.WHITE;
	private Color up = new Color(59, 164, 59);
	private Color down = new Color(224, 27, 28);
	private Color gap = new Color(245, 245, 245);

	public PriceCandleCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.borderLeft = 100;
		this.borderRight = 50;
		this.borderTop = 50;
		this.borderBottom = 100;
		this.image = new BufferedImage(width, height, TYPE_INT_RGB);
		this.graphics = (Graphics2D) image.getGraphics();
	}

	public void renderCandles(Collection<? extends IPriceCandle> candles, IPriceInterval interval) {

		// Clear the canvas by rendering the background
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);

		// Determine points per pixel
		IPriceCandle merged = PriceCandles.merge(candles);
		double pointsPerPixel = getPointsPerPixel(merged);

		// Determine candle width in pixels
		int candleWidth = getWidthInPixels(candles.size());

		// Render candles
		int index = 0;
		long previousOpen = 0;
		for (IPriceCandle candle : candles) {

			// Handle missing candles
			index = renderMissingCandle(candle, interval, previousOpen, index, candleWidth);
			previousOpen = interval.getTimePeriod(candle);

			renderCandle(candle, index++, candleWidth, pointsPerPixel, merged.getMidLowPrice());
		}

		renderXAxis(index, candleWidth);
		renderYAxis(merged);
	}

	private void renderYAxis(IPriceCandle merged) {
		int x = borderLeft - yAxisOffset;
		int y1 = borderTop;
		int y2 = height - borderBottom + xAxisOffset;

		graphics.setColor(Color.BLACK);
		graphics.drawLine(x, y1, x, y2);

		String lowText = toText(merged.getMidLowPrice(), merged.getDecimalPlaces());
		graphics.drawString(lowText, x - getPixelLength(lowText) - 8, y2 - xAxisOffset);

		String highText = toText(merged.getMidHighPrice(), merged.getDecimalPlaces());
		graphics.drawString(highText, x - getPixelLength(highText) - 8, y1);
	}

	protected int getPixelLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (i > 0) {
				length += 2;
			}
			char c = text.charAt(i);
			if (c == '.') {
				length += 2;
			} else {
				length += 5;
			}
		}
		return length;
	}

	private String toText(int price, int decimalPlaces) {
		return Decimals.toBigDecimal(price, decimalPlaces).toString();
	}

	private void renderXAxis(int index, int candleWidth) {

		int x1 = borderLeft - yAxisOffset;
		int y = height - borderBottom + xAxisOffset;
		int x2 = width - borderRight;

		graphics.setColor(Color.BLACK);
		graphics.drawLine(x1, y, x2, y);
	}

	private int renderMissingCandle(IPriceCandle candle, IPriceInterval interval, long previousOpen, int index, int candleWidth) {
		if (index == 0) {
			return 0;
		}

		while (true) {
			previousOpen += interval.getLength();
			long currentOpen = interval.getTimePeriod(candle);
			if (previousOpen >= currentOpen) {
				break;
			}

			// Render missing candle
			int x = getCandleX(index, candleWidth);
			int y = borderTop;
			int gapWidth = candleWidth;
			int gapHeight = height - (borderTop + borderBottom);
			graphics.setColor(gap);
			graphics.fillRect(x, y, gapWidth, gapHeight);

			index++;
		}

		return index;

	}

	private int getWidthInPixels(int candles) {
		int widthInPixels = (width - (borderLeft + borderRight)) - ((candles * candleSpacing) - 1);

		int pixelsPerCandle = widthInPixels / candles;
		if (pixelsPerCandle % 2 == 0) {
			pixelsPerCandle--;
		}
		if (pixelsPerCandle <= 1) {
			throw new IllegalArgumentException("Too many candles to render correctly");
		}
		return pixelsPerCandle;
	}

	private void renderCandle(IPriceCandle candle, int index, int candleWidth, double pointsPerPixel, int basePrice) {

		// Determine x coordinate, taking in to account border and other candles
		int x = getCandleX(index, candleWidth);

		// Which color?
		graphics.setColor(candle.hasClosedUp() ? up : down);

		// Draw the wick
		int wickX = x + (candleWidth / 2);
		double high = candle.getMidHighPrice();
		double low = candle.getMidLowPrice();

		int lowY = (int) ((low - basePrice) / pointsPerPixel);
		int highY = (int) ((high - basePrice) / pointsPerPixel);

		// Graphics render from top left, however candles price from bottom left
		// We have to flip the y axis
		int zeroPixel = height - borderBottom;
		lowY = zeroPixel - lowY;
		highY = zeroPixel - highY;
		graphics.drawLine(wickX, highY, wickX, lowY);

		// Draw the candle
		double open = candle.getMidOpenPrice();
		double close = candle.getMidClosePrice();
		int openY = (int) ((open - basePrice) / pointsPerPixel);
		int closeY = (int) ((close - basePrice) / pointsPerPixel);

		openY = zeroPixel - openY;
		closeY = zeroPixel - closeY;

		// Special case - draw single pixel line where open and close too close
		if (openY == closeY) {
			openY = closeY + 1;
		}

		if (openY > closeY) {
			graphics.fillRect(x, closeY, candleWidth, openY - closeY);
		} else {
			graphics.fillRect(x, openY, candleWidth, closeY - openY);
		}

	}

	private int getCandleX(int index, int candleWidth) {
		return borderLeft + (index * (candleWidth + candleSpacing));
	}

	private double getPointsPerPixel(IPriceCandle merged) {
		long points = merged.getHighLowRange();
		long pixels = height - (borderTop + borderBottom);
		return (double) points / (double) pixels;
	}

	public byte[] toGif() {
		Stopwatch timer = Stopwatch.createStarted();
		byte[] bytes = Images.toGif(image);
		timer.stop();
		log.info("Serialized {} to GIF in {}", bytes(bytes), timer);
		return bytes;
	}

	public byte[] toPng() {
		Stopwatch timer = Stopwatch.createStarted();
		byte[] bytes = Images.toPng(image);
		timer.stop();
		log.info("Serialized {} to PNG in {}", bytes(bytes), timer);
		return bytes;
	}

	public void writeAsGif(String filename) {
		Stopwatch timer = Stopwatch.createStarted();
		Images.writeAsGif(image, new File(filename));
		timer.stop();
		log.info("Wrote {} in {}", filename, timer);
	}

	public void writeAsPng(String filename) throws IOException {
		Stopwatch timer = Stopwatch.createStarted();
		Images.writeAsPng(image, new File(filename));
		timer.stop();
		log.info("Wrote {} in {}", filename, timer);
	}

}
