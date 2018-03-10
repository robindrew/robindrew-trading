package com.robindrew.trading.price.candle.format.pcf.source.resource;

import java.net.URL;
import java.time.LocalDate;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.format.pcf.source.PcfSource;

public class PcfResource extends PcfSource implements IPcfResource {

	public PcfResource(String resourceName, LocalDate month) {
		super(resourceName, month);
	}

	public PcfResource(String resourceName) {
		this(resourceName, PcfFormat.getMonth(resourceName));
	}

	public String getResourceName() {
		return getName();
	}

	@Override
	public URL getURL() {
		return Resources.getResource(getName());
	}

	@Override
	public ByteSource toByteSource() {
		return Resources.asByteSource(getURL());
	}

	@Override
	public ByteSink toByteSink() {
		// You cannot write to resources
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean exists() {
		try {
			Resources.getResource(getName());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean create() {
		return false;
	}
}
