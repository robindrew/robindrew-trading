package com.robindrew.trading.price.candle.format.ptf.source.resource;

import java.net.URL;
import java.time.LocalDate;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.robindrew.trading.price.candle.format.PriceFormat;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.format.ptf.source.ByteStreamPtfSource;

public class PtfResource extends ByteStreamPtfSource implements IPtfResource {

	public PtfResource(String resourceName, LocalDate day) {
		super(resourceName, day);
	}

	public PtfResource(String resourceName) {
		this(resourceName, PtfFormat.getDay(resourceName));
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

	@Override
	public PriceFormat getFormat() {
		return PriceFormat.PTF;
	}
}
