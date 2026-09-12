package com.robindrew.trading.util.lang.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * An {@link IField} backed by a {@link Field}.
 */
public class ReflectedField implements IField {

    private final Field field;

    public ReflectedField(Field field) {
        if (field == null) {
            throw new NullPointerException("field");
        }
        field.setAccessible(true);
        this.field = field;
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(field.getModifiers());
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to read field: " + field, e);
        }
    }

    @Override
    public String toString() {
        return field.toString();
    }
}
