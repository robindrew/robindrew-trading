package com.robindrew.trading;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.robindrew.common.util.Check;

public class Instrument implements IInstrument {

	private final String name;
	private final InstrumentType type;
	private final IInstrument underlying;

	public Instrument(String name, InstrumentType type) {
		this.name = Check.notEmpty("name", name);
		this.type = Check.notNull("type", type);
		this.underlying = null;
	}

	public Instrument(String name, IInstrument underlying) {
		this.name = Check.notEmpty("name", name);
		this.underlying = Check.notNull("underlying", underlying);
		this.type = underlying.getType();
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
		return name.hashCode() + type.hashCode();
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

}
