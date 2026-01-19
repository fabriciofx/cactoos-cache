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
 * EntryEnvelope.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 * @checkstyle DesignForExtensionCheck (200 lines)
 */
public abstract class EntryEnvelope<D, V> implements Entry<D, V> {
    /**
     * Key.
     */
    private final Key<D> id;

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
    public EntryEnvelope(final Key<D> key, final V value) {
        this(key, value, new MapOf<>());
    }

    /**
     * Ctor.
     * @param key A key
     * @param value A value
     * @param metadata The metadata
     */
    public EntryEnvelope(
        final Key<D> key,
        final V value,
        final Map<String, List<String>> metadata
    ) {
        this.id = key;
        this.val = value;
        this.meta = metadata;
    }

    @Override
    public Key<D> key() {
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
