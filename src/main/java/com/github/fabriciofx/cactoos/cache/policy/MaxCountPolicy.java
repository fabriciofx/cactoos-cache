/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policy;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Evicted;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import org.cactoos.Bytes;

/**
 * MaxCountPolicy.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.12
 */
public final class MaxCountPolicy<K extends Bytes, V extends Bytes>
    implements Policy<K, V> {
    /**
     * Max count.
     */
    private final int max;

    /**
     * Ctor.
     */
    public MaxCountPolicy() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     *
     * @param max Maximum size
     */
    public MaxCountPolicy(final int max) {
        this.max = max;
    }

    @Override
    public void apply(final Cache<K, V> cache) {
        final Evicted<K, V> evicted = cache.evicted();
        final Store<K, V> store = cache.store();
        final Entries<K, V> entries = store.entries();
        while (entries.count() > this.max) {
            evicted.add(store.delete(store.keys().iterator().next()));
        }
    }
}
