package com.robindrew.trading.util.lang.field;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists the fields declared by a type and its supertypes.
 * <p>
 * Static and final fields are excluded unless explicitly included.
 */
public class FieldLister {

    private boolean includeStatic = false;
    private boolean includeFinal = false;
    private boolean includeSuperclasses = true;

    public FieldLister includeStatic(boolean include) {
        this.includeStatic = include;
        return this;
    }

    public FieldLister includeFinal(boolean include) {
        this.includeFinal = include;
        return this;
    }

    public FieldLister includeSuperclasses(boolean include) {
        this.includeSuperclasses = include;
        return this;
    }

    /**
     * Returns the matching fields of the given type.
     */
    public List<IField> getFieldList(Class<?> type) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        List<IField> fields = new ArrayList<>();
        for (Class<?> current = type; current != null && !Object.class.equals(current); ) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isSynthetic()) {
                    continue;
                }
                IField element = new ReflectedField(field);
                if (!includeStatic && element.isStatic()) {
                    continue;
                }
                if (!includeFinal && element.isFinal()) {
                    continue;
                }
                fields.add(element);
            }
            current = includeSuperclasses ? current.getSuperclass() : null;
        }
        return ImmutableList.copyOf(fields);
    }
}
