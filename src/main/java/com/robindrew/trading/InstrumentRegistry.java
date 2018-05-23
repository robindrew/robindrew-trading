package com.robindrew.trading;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.robindrew.common.collect.copyonwrite.CopyOnWriteMap;
import com.robindrew.common.lang.reflect.field.FieldLister;
import com.robindrew.common.lang.reflect.field.IField;

public class InstrumentRegistry implements IInstrumentRegistry {

	private final Map<Class<?>, Registry<?>> registryMap = new CopyOnWriteMap<>();

	public <I extends IInstrument> void register(Class<I> type) {
		Registry<I> registry = new Registry<I>(type);
		registryMap.putIfAbsent(type, registry);

		// Look for implemented interfaces and map them too
		for (Class<?> implemented : type.getInterfaces()) {
			if (!IInstrument.class.isAssignableFrom(implemented)) {
				continue;
			}
			if (IInstrument.class.equals(implemented)) {
				continue;
			}
			registryMap.putIfAbsent(implemented, registry);
		}

	}

	@SuppressWarnings("unchecked")
	public <I extends IInstrument> Optional<I> get(IInstrument instrument, Class<I> type) {
		Registry<I> registry = (Registry<I>) registryMap.get(type);
		if (registry == null) {
			return Optional.absent();
		}
		return registry.get(instrument);
	}

	public class Registry<I extends IInstrument> {

		private final Class<I> type;
		private final Set<I> instruments = new TreeSet<>();

		private Registry(Class<I> type) {
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

		public Optional<I> get(IInstrument instrument) {
			for (I element : instruments) {
				if (element.matches(instrument)) {
					return Optional.of(element);
				}
			}
			return Optional.absent();
		}
	}
}
