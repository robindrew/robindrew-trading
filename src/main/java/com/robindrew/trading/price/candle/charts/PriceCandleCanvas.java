package com.robindrew.trading.price.candle.charts;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.robindrew.trading.Instruments;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.provider.TradeDataProviderSet;

public class PriceCandleCanvas {

	public static void main(String[] args) throws Throwable {

		String directory = "C:\\development\\repository\\git\\robindrew-public\\robindrew-trading-histdata-data\\data\\pcf";
		IPcfSourceManager manager = new PcfFileManager(new File(directory), TradeDataProviderSet.defaultProviders());

		IPcfSourceSet set = manager.getSourceSet(Instruments.USD_JPY);
		IPcfSource source = set.getSource(LocalDate.of(2017, 9, 01));
		List<IPriceCandle> candles = source.read();
		candles = candles.subList(0, 220);

		int width = 1120;
		int height = 700;

		PriceCandleCanvas canvas = new PriceCandleCanvas(width, height);
		canvas.renderCandles(candles);
		canvas.writeAsGif("c:/temp/pcg.gif");
	}

	// Immutable
	private final int width;
	private final int height;
	private final BufferedImage image;
	private final Graphics2D graphics;

	// Mutable
	private int borderX = 20;
	private int borderY = 20;
	private int candleSpacing = 1;
	private Color background = Color.WHITE;
	private Color up = Color.GREEN;
	private Color down = Color.RED;

	public PriceCandleCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.borderX = width / 10;
		this.borderY = height / 10;
		this.image = new BufferedImage(width, height, TYPE_INT_RGB);
		this.graphics = (Graphics2D) image.getGraphics();
	}

	public void renderCandles(List<IPriceCandle> candles) {

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
		for (IPriceCandle candle : candles) {
			renderCandle(candle, index++, candleWidth, pointsPerPixel, merged.getLowPrice());
		}
	}

	private int getWidthInPixels(int candles) {
		int widthInPixels = (width - (borderX * 2)) - ((candles * candleSpacing) - 1);
		int pixelsPerCandle = widthInPixels / candles;
		if (pixelsPerCandle % 2 == 0) {
			pixelsPerCandle--;
		}
		if(pixelsPerCandle <= 1) {
			throw new IllegalArgumentException("Too many candles to render correctly");
		}
		return pixelsPerCandle;
	}

	private void renderCandle(IPriceCandle candle, int index, int candleWidth, double pointsPerPixel, int basePrice) {

		// Determine x coordinate, taking in to account border and other candles
		int x = borderX + (index * (candleWidth + candleSpacing));

		// Which color?
		graphics.setColor(candle.hasClosedUp() ? up : down);

		// Draw the wick
		int wickX = x + (candleWidth / 2);
		double high = candle.getHighPrice();
		double low = candle.getLowPrice();

		int lowY = (int) ((low - basePrice) / pointsPerPixel);
		int highY = (int) ((high - basePrice) / pointsPerPixel);

		// Graphics render from top left, however candles price from bottom left
		// We have to flip the y axis
		int zeroPixel = height - borderY;
		lowY = zeroPixel - lowY;
		highY = zeroPixel - highY;
		graphics.drawLine(wickX, highY, wickX, lowY);

		// Draw the candle
		double open = candle.getOpenPrice();
		double close = candle.getClosePrice();
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

	private double getPointsPerPixel(IPriceCandle merged) {
		long points = merged.getHighLowRange();
		long pixels = height - (borderY * 2);
		return (double) points / (double) pixels;
	}

	public void render(int x, int y, int width, int height, IPriceCandle candle) {
		if (width < 1 || width % 2 == 0) {
			throw new IllegalArgumentException("width=" + width);
		}
		if (height < 1) {
			throw new IllegalArgumentException("height=" + height);
		}

		// Which color?
		Color color = candle.hasClosedUp() ? Color.GREEN : Color.RED;
		graphics.setColor(color);

		// Draw wick
		int wickX = x + (width / 2);
		graphics.drawLine(wickX, y, wickX, y + height);

		long range = candle.getHighLowRange();
		double pixelsPerPoint = (double) height / (double) range;

		int y1 = y + (int) (pixelsPerPoint * (candle.getHighPrice() - candle.getClosePrice()));
		int y2 = y + (int) (pixelsPerPoint * (candle.getHighPrice() - candle.getOpenPrice()));

		System.out.println("x=" + x);
		System.out.println("y1=" + y1);
		System.out.println("y2=" + y2);

		if (y1 > y2) {
			graphics.fillRect(x, y1, width, y1 - y2);
		} else {
			graphics.fillRect(x, y2, width, y2 - y1);
		}
	}

	public void writeAsGif(File file) throws IOException {
		ImageIO.write(image, "GIF", file);
	}

	public void writeAsGif(String filename) throws IOException {
		writeAsGif(new File(filename));
	}

}
