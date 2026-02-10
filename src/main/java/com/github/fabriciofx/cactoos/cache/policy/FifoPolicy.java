/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policy;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Evicted;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import org.cactoos.Bytes;

/**
 * MaxSizePolicy.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 */
public final class FifoPolicy<K extends Bytes, V extends Bytes>
    implements Policy<K, V> {
    /**
     * Max size.
     */
    private final int max;

    /**
     * Ctor.
     */
    public FifoPolicy() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     *
     * @param max Maximum cache size
     */
    public FifoPolicy(final int max) {
        this.max = max;
    }

    @Override
    public void apply(final Cache<K, V> cache) {
        final Evicted<K, V> evicted = cache.evicted();
        final Store<K, V> store = cache.store();
        while (cache.size() > this.max) {
            evicted.add(store.delete(store.keys().iterator().next()));
        }
    }
}
