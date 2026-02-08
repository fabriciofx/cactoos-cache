/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entry;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Metadata;
import org.cactoos.Bytes;

/**
 * InvalidEntry.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public final class InvalidEntry<K extends Bytes, V extends Bytes>
    implements Entry<K, V> {
    @Override
    public Key<K> key() {
        throw new UnsupportedOperationException("#key(): invalid entry");
    }

    @Override
    public V value() {
        throw new UnsupportedOperationException("#value(): invalid entry");
    }

    @Override
    public Metadata metadata() {
        throw new UnsupportedOperationException("#metadata(): invalid entry");
    }

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
