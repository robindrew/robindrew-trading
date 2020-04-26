package com.robindrew.trading;

import java.util.Optional;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.range.IPriceRange;

public class Instrument implements IInstrument {

	private final String name;
	private final InstrumentType type;
	private final IInstrument underlying;
	private final Optional<IPriceRange> range;

	public Instrument(String name, InstrumentType type) {
		this.name = Check.notEmpty("name", name);
		this.type = Check.notNull("type", type);
		this.underlying = null;
		this.range = Optional.empty();
	}

	public Instrument(String name, InstrumentType type, IPriceRange range) {
		this.name = Check.notEmpty("name", name);
		this.type = Check.notNull("type", type);
		this.underlying = null;
		this.range = Optional.of(range);
	}

	public Instrument(String name, IInstrument underlying) {
		this.name = Check.notEmpty("name", name);
		this.underlying = Check.notNull("underlying", underlying);
		this.type = underlying.getType();
		this.range = Optional.empty();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public InstrumentType getType() {
		return type;
	}

	@Override
	public String toString() {
		return type + "[" + name + "]";
	}

	@Override
	public IInstrument getUnderlying() {
		return underlying == null ? this : underlying;
	}

	@Override
	public IInstrument getUnderlying(boolean recursive) {
		if (!recursive) {
			return getUnderlying();
		}
		return underlying == null ? this : underlying.getUnderlying(true);
	}

	@Override
	public int hashCode() {
		return (name.hashCode() + type.hashCode()) * 1999;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof IInstrument) {
			IInstrument that = (IInstrument) object;
			if (!this.getType().equals(that.getType())) {
				return false;
			}
			return this.getName().equals(that.getName());
		}
		return false;
	}

	@Override
	public int compareTo(IInstrument that) {
		CompareToBuilder compare = new CompareToBuilder();
		compare.append(this.getType(), that.getType());
		compare.append(this.getName(), that.getName());
		return compare.toComparison();
	}

	@Override
	public boolean matches(IInstrument instrument) {
		if (this.equals(instrument)) {
			return true;
		}
		if (underlying != null) {
			return underlying.matches(instrument);
		}
		return false;
	}

	@Override
	public Optional<IPriceRange> getRange() {
		return range;
	}

}
