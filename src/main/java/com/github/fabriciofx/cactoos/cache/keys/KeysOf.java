/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.keys;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import java.util.Iterator;
import java.util.Set;

/**
 * KeysOf.
 * @param <D> the key domain type
 * @since 0.0.1
 */
public final class KeysOf<D> implements Keys<D> {
    /**
     * Keys.
     */
    private final Set<Key<D>> keys;

    /**
     * Ctor.
     * @param keys The keys
     */
    public KeysOf(final Set<Key<D>> keys) {
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
    public Iterator<Key<D>> iterator() {
        return this.keys.iterator();
    }
}
