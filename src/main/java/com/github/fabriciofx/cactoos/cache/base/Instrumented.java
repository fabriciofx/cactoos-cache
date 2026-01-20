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

/**
 * Instrumented Cache.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnnecessaryFullyQualifiedName")
public final class Instrumented<D, V> implements Cache<D, V> {
    /**
     * Cache.
     */
    private final Cache<D, V> origin;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     * @param cache The cache
     */
    public Instrumented(final Cache<D, V> cache) {
        this(
            cache,
            new StatisticsOf(
                new Hits(),
                new Misses(),
                new Lookups(),
                new Invalidations(),
                new Evictions()
            )
        );
    }

    /**
     * Ctor.
     * @param cache The cache
     * @param statistics The statistics
     */
    public Instrumented(final Cache<D, V> cache, final Statistics statistics) {
        this.origin = cache;
        this.stats = statistics;
    }

    @Override
    public Store<D, V> store() {
        return new com.github.fabriciofx.cactoos.cache.store.Instrumented<>(
            this.origin.store(),
            this.stats
        );
    }

    @Override
    public Statistics statistics() {
        return this.stats;
    }

    @Override
    public void clear() {
        this.store().keys().clear();
        this.store().entries().clear();
    }
}
