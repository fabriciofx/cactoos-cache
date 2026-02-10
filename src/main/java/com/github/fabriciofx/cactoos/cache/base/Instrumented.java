/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Evicted;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import org.cactoos.Bytes;

/**
 * Instrumented Cache.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public final class Instrumented<K extends Bytes, V extends Bytes>
    implements Cache<K, V> {
    /**
     * Cache.
     */
    private final Cache<K, V> origin;

    /**
     * Ctor.
     * @param cache The cache
     */
    public Instrumented(final Cache<K, V> cache) {
        this.origin = cache;
    }

    @Override
    public Store<K, V> store() {
        return new com.github.fabriciofx.cactoos.cache.store.Instrumented<>(
            this.origin.store(),
            this.origin.statistics()
        );
    }

    @Override
    public Statistics statistics() {
        return this.origin.statistics();
    }

    @Override
    public Evicted<K, V> evicted() {
        return new com.github.fabriciofx.cactoos.cache.evicted.Instrumented<>(
            this.origin.evicted(),
            this.origin.statistics()
        );
    }

    @Override
    public void clear() {
        this.origin.clear();
        this.origin.statistics().reset();
    }

    @Override
    public int size() {
        return this.origin.size();
    }
}
