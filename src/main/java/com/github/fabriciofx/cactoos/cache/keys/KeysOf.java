/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.keys;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import java.util.Iterator;
import java.util.Set;
import org.cactoos.Bytes;

/**
 * KeysOf.
 * @param <K> the key value type
 * @since 0.0.1
 */
public final class KeysOf<K extends Bytes> implements Keys<K> {
    /**
     * Keys.
     */
    private final Set<Key<K>> keys;

    /**
     * Ctor.
     * @param keys The keys
     */
    public KeysOf(final Set<Key<K>> keys) {
        this.keys = keys;
    }

    @Override
    public int count() {
        return this.keys.size();
    }

    @Override
    public void clear() {
        this.keys.clear();
    }

    @Override
    public Iterator<Key<K>> iterator() {
        return this.keys.iterator();
    }
}
