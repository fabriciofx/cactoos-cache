/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entry;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import java.util.List;
import java.util.Map;

/**
 * InvalidEntry.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public final class InvalidEntry<D, V> implements Entry<D, V> {
    @Override
    public Key<D> key() {
        throw new UnsupportedOperationException("#key(): invalid entry");
    }

    @Override
    public V value() {
        throw new UnsupportedOperationException("#value(): invalid entry");
    }

    @Override
    public Map<String, List<String>> metadata() {
        throw new UnsupportedOperationException("#metadata(): invalid entry");
    }

    @Override
    public boolean valid() {
        return false;
    }
}
