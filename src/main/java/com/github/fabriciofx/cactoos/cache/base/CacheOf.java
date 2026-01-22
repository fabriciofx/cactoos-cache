/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.statistic.Evictions;
import com.github.fabriciofx.cactoos.cache.statistic.Hits;
import com.github.fabriciofx.cactoos.cache.statistic.Invalidations;
import com.github.fabriciofx.cactoos.cache.statistic.Lookups;
import com.github.fabriciofx.cactoos.cache.statistic.Misses;
import com.github.fabriciofx.cactoos.cache.statistics.StatisticsOf;
import com.github.fabriciofx.cactoos.cache.store.StoreOf;

/**
 * CacheOf.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class CacheOf<D, V> implements Cache<D, V> {
    /**
     * Store.
     */
    private final Store<D, V> str;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     */
    public CacheOf() {
        this(new StoreOf<>());
    }

    /**
     * Ctor.
     * @param store A store
     */
    public CacheOf(final Store<D, V> store) {
        this(
            store,
            new StatisticsOf(
                new Evictions(),
                new Hits(),
                new Invalidations(),
                new Lookups(),
                new Misses()
            )
        );
    }

    /**
     * Ctor.
     * @param store A store
     * @param statistics The statistics
     */
    public CacheOf(
        final Store<D, V> store,
        final Statistics statistics
    ) {
        this.str = store;
        this.stats = statistics;
    }

    @Override
    public Store<D, V> store() {
        return this.str;
    }

    @Override
    public Statistics statistics() {
        return this.stats;
    }

    @Override
    public void clear() {
        this.str.keys().clear();
        this.str.entries().clear();
        this.stats.reset();
    }
}
