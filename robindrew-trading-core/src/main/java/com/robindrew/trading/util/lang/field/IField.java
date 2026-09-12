package com.robindrew.trading.util.lang.field;

import java.lang.reflect.Field;

/**
 * A reflective view of a single field.
 */
public interface IField {

    /**
     * Returns the name of the field.
     */
    String getName();

    /**
     * Returns the declared type of the field.
     */
    Class<?> getType();

    /**
     * Returns true if the field is static.
     */
    boolean isStatic();

    /**
     * Returns true if the field is final.
     */
    boolean isFinal();

    /**
     * Returns the underlying reflective field.
     */
    Field getField();

    /**
     * Returns the value of this field for the given instance (null for a static field).
     */
    <T> T get(Object instance);
}
