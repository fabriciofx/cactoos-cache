/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.store;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import org.cactoos.Bytes;

/**
 * Instrumented Store.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public final class Instrumented<K extends Bytes, V extends Bytes>
    implements Store<K, V> {
    /**
     * Store.
     */
    private final Store<K, V> origin;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     * @param store The store
     * @param statistics The statistics
     */
    public Instrumented(final Store<K, V> store, final Statistics statistics) {
        this.origin = store;
        this.stats = statistics;
    }

    @Override
    public Entry<K, V> retrieve(final Key<K> key) {
        this.stats.statistic("lookups").increment(1);
        final Entry<K, V> entry = this.origin.retrieve(key);
        if (entry.valid()) {
            this.stats.statistic("hits").increment(1);
        } else {
            this.stats.statistic("misses").increment(1);
        }
        return entry;
    }

    @Override
    public Entry<K, V> save(final Key<K> key, final Entry<K, V> entry) {
        final Entry<K, V> replaced = this.origin.save(key, entry);
        if (replaced.valid()) {
            this.stats.statistic("replacements").increment(1);
        } else {
            this.stats.statistic("insertions").increment(1);
        }
        return replaced;
    }

    @Override
    public Entry<K, V> delete(final Key<K> key) {
        this.stats.statistic("invalidations").increment(1);
        return this.origin.delete(key);
    }

    @Override
    public boolean contains(final Key<K> key) {
        final boolean exists = this.origin.contains(key);
        if (exists) {
            this.stats.statistic("hits").increment(1);
        } else {
            this.stats.statistic("misses").increment(1);
        }
        this.stats.statistic("lookups").increment(1);
        return exists;
    }

    @Override
    public Keys<K> keys() {
        return new com.github.fabriciofx.cactoos.cache.keys.Instrumented<>(
            this.origin.keys(),
            this.stats
        );
    }

    @Override
    public Entries<K, V> entries() {
        return new com.github.fabriciofx.cactoos.cache.entries.Instrumented<>(
            this.origin.entries(),
            this.stats
        );
    }
}
