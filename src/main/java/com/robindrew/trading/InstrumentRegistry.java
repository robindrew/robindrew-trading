package com.robindrew.trading;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.collect.copyonwrite.CopyOnWriteMap;
import com.robindrew.common.lang.reflect.field.FieldLister;
import com.robindrew.common.lang.reflect.field.IField;

public class InstrumentRegistry<I extends IInstrument> {

	private static final Map<Class<?>, InstrumentRegistry<?>> registryMap = new CopyOnWriteMap<>();

	public static <I extends IInstrument> void register(Class<I> type) {
		InstrumentRegistry<I> registry = new InstrumentRegistry<I>(type);
		registryMap.putIfAbsent(type, registry);
	}

	@SuppressWarnings("unchecked")
	public static <I extends IInstrument> I get(Class<I> type, IInstrument instrument) {
		InstrumentRegistry<I> registry = (InstrumentRegistry<I>) registryMap.get(type);
		if (registry == null) {
			throw new IllegalArgumentException("Type not registered: " + type);
		}
		return registry.get(instrument);
	}

	private final Class<I> type;
	private final Set<I> instruments = new TreeSet<>();

	public InstrumentRegistry(Class<I> type) {
		this.type = type;

		FieldLister lister = new FieldLister();
		lister.includeStatic(true);
		lister.includeFinal(true);
		for (IField field : lister.getFieldList(type)) {
			if (field.isStatic() && IInstrument.class.isAssignableFrom(field.getType())) {
				I instrument = field.get(null);
				instruments.add(instrument);
			}
		}

		// Sanity check
		if (instruments.isEmpty()) {
			throw new IllegalStateException("No instruments found in type: " + type);
		}
	}

	public Class<I> getType() {
		return type;
	}

	public Set<I> getInstruments() {
		return ImmutableSet.copyOf(instruments);
	}

	public I get(IInstrument instrument) {
		for (I element : instruments) {
			if (element.matches(instrument)) {
				return element;
			}
		}
		throw new IllegalArgumentException("Unable to find a match for " + instrument + " in type " + type);
	}
}
