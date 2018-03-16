package com.robindrew.trading.price.candle.charts.jfreechart;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;

import com.google.common.io.ByteSink;
import com.robindrew.common.image.ImageFormat;
import com.robindrew.common.util.Java;
import com.robindrew.trading.price.candle.charts.IPriceCandleChart;

public class JFreePriceCandleChart implements IPriceCandleChart {

	private final JFreeChart chart;
	private final int width;
	private final int height;

	private ChartRenderingInfo info = null;

	public JFreePriceCandleChart(JFreeChart chart, int width, int height) {
		if (chart == null) {
			throw new NullPointerException("chart");
		}
		if (width < 10) {
			throw new IllegalArgumentException("width=" + width);
		}
		if (height < 10) {
			throw new IllegalArgumentException("height=" + height);
		}
		this.chart = chart;
		this.width = width;
		this.height = height;
	}

	@Override
	public void writeTo(ByteSink sink, ImageFormat format) {
		try (OutputStream output = sink.openBufferedStream()) {
			int type = getType(format);
			BufferedImage image = chart.createBufferedImage(width, height, type, info);
			EncoderUtil.writeBufferedImage(image, getFormat(format), output);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	private String getFormat(ImageFormat format) {
		switch (format) {
			case JPG:
				return "jpeg";
			case PNG:
				return "png";
			default:
				throw new IllegalArgumentException("format not supported: " + format);
		}
	}

	private int getType(ImageFormat format) {
		switch (format) {
			case JPG:
				return BufferedImage.TYPE_INT_RGB;
			case PNG:
				return BufferedImage.TYPE_INT_ARGB;
			default:
				throw new IllegalArgumentException("format not supported: " + format);
		}
	}

	public void setInfo(ChartRenderingInfo info) {
		if (info == null) {
			throw new NullPointerException("info");
		}
		this.info = info;
	}

}