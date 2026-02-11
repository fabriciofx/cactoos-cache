/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Evicted;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.evicted.EvictedOf;
import com.github.fabriciofx.cactoos.cache.statistic.Evictions;
import com.github.fabriciofx.cactoos.cache.statistic.Hits;
import com.github.fabriciofx.cactoos.cache.statistic.Insertions;
import com.github.fabriciofx.cactoos.cache.statistic.Invalidations;
import com.github.fabriciofx.cactoos.cache.statistic.Lookups;
import com.github.fabriciofx.cactoos.cache.statistic.Misses;
import com.github.fabriciofx.cactoos.cache.statistic.Replacements;
import com.github.fabriciofx.cactoos.cache.statistics.StatisticsOf;
import com.github.fabriciofx.cactoos.cache.store.StoreOf;
import org.cactoos.Bytes;

/**
 * CacheOf.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class CacheOf<K extends Bytes, V extends Bytes>
    implements Cache<K, V> {
    /**
     * Store.
     */
    private final Store<K, V> str;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Evicted.
     */
    private final Evicted<K, V> removed;

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
    public CacheOf(final Store<K, V> store) {
        this(
            store,
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
     * @param store A store
     * @param statistics Statistics
     */
    public CacheOf(final Store<K, V> store, final Statistics statistics) {
        this(store, statistics, new EvictedOf<>());
    }

    /**
     * Ctor.
     * @param store A store
     * @param statistics Statistics
     * @param evicted The evicted entries
     */
    public CacheOf(
        final Store<K, V> store,
        final Statistics statistics,
        final Evicted<K, V> evicted
    ) {
        this.str = store;
        this.stats = statistics;
        this.removed = evicted;
    }

    @Override
    public Store<K, V> store() {
        return this.str;
    }

    @Override
    public Statistics statistics() {
        return this.stats;
    }

    @Override
    public Evicted<K, V> evicted() {
        return this.removed;
    }

    @Override
    public void clear() {
        this.str.entries().clear();
        this.removed.clear();
    }

    @Override
    public int size() {
        return this.str.keys().size() + this.str.entries().size();
    }
}
