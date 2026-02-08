/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entry;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Metadata;
import com.github.fabriciofx.cactoos.cache.metadata.MetadataOf;
import org.cactoos.Bytes;
import org.cactoos.bytes.UncheckedBytes;

/**
 * EntryOf.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class EntryOf<K extends Bytes, V extends Bytes>
    implements Entry<K, V> {
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
    private final Metadata meta;

    /**
     * Ctor.
     * @param key A key
     * @param value A value
     */
    public EntryOf(final Key<K> key, final V value) {
        this(key, value, new MetadataOf());
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
        final Metadata metadata
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
    public Metadata metadata() {
        return this.meta;
    }

    @Override
    public boolean valid() {
        return true;
    }

    @Override
    public int size() {
        return new UncheckedBytes(this.val).asBytes().length;
    }
}
