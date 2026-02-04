/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.metadata;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Type of T.
 *
 * @param <T> The type parameter
 * @since 0.0.7
 * @checkstyle NonStaticMethodCheck (100 lines)
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class TypeOf<T> {
    /**
     * The type.
     */
    private final Type typ;

    /**
     * Ctor.
     */
    protected TypeOf() {
        this.typ = ((ParameterizedType)
            this.getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

    /**
     * Returns the type.
     * @return The type
     */
    public Type type() {
        return this.typ;
    }

    /**
     * Casts an object to T.
     * @param value The object
     * @return The casted object
     */
    @SuppressWarnings("unchecked")
    public T cast(final Object value) {
        return (T) value;
    }
}
