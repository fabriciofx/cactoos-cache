/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entry;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import java.util.List;
import java.util.Map;
import org.cactoos.map.MapOf;

/**
 * EntryOf.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class EntryOf<K, V> implements Entry<K, V> {
    /**
     * Key.
     */
    private final Key<K> id;

    /**
     * Value.
     */
    private final V val;

    /**
     * Metadata.
     */
    private final Map<String, List<String>> meta;

    /**
     * Ctor.
     * @param key A key
     * @param value A value
     */
    public EntryOf(final Key<K> key, final V value) {
        this(key, value, new MapOf<>());
    }

    /**
     * Ctor.
     * @param key A key
     * @param value A value
     * @param metadata The metadata
     */
    public EntryOf(
        final Key<K> key,
        final V value,
        final Map<String, List<String>> metadata
    ) {
        this.id = key;
        this.val = value;
        this.meta = metadata;
    }

    @Override
    public Key<K> key() {
        return this.id;
    }

    @Override
    public V value() {
        return this.val;
    }

    @Override
    public Map<String, List<String>> metadata() {
        return this.meta;
    }

    @Override
    public boolean valid() {
        return true;
    }
}
