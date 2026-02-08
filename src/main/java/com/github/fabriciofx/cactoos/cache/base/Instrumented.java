/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.statistic.Evictions;
import com.github.fabriciofx.cactoos.cache.statistic.Hits;
import com.github.fabriciofx.cactoos.cache.statistic.Insertions;
import com.github.fabriciofx.cactoos.cache.statistic.Invalidations;
import com.github.fabriciofx.cactoos.cache.statistic.Lookups;
import com.github.fabriciofx.cactoos.cache.statistic.Misses;
import com.github.fabriciofx.cactoos.cache.statistic.Replacements;
import com.github.fabriciofx.cactoos.cache.statistics.StatisticsOf;
import java.util.List;
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
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     * @param cache The cache
     */
    public Instrumented(final Cache<K, V> cache) {
        this(
            cache,
            new StatisticsOf(
                new Hits(),
                new Misses(),
                new Lookups(),
                new Invalidations(),
                new Evictions(),
                new Insertions(),
                new Replacements()
            )
        );
    }

    /**
     * Ctor.
     * @param cache The cache
     * @param statistics The statistics
     */
    public Instrumented(final Cache<K, V> cache, final Statistics statistics) {
        this.origin = cache;
        this.stats = statistics;
    }

    @Override
    public Store<K, V> store() {
        return new com.github.fabriciofx.cactoos.cache.store.Instrumented<>(
            this.origin.store(),
            this.stats
        );
    }

    @Override
    public Statistics statistics() {
        final List<Entry<K, V>> evicted = this.origin.evicted();
        this.stats.statistic("evictions").increment(evicted.size());
        return this.stats;
    }

    @Override
    public List<Entry<K, V>> evicted() {
        return this.origin.evicted();
    }

    @Override
    public void clear() {
        this.origin.clear();
        this.stats.reset();
    }

    @Override
    public int size() {
        return this.origin.size();
    }
}
