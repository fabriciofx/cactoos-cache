/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.evicted;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Evicted;
import com.github.fabriciofx.cactoos.cache.Statistics;
import org.cactoos.Bytes;

/**
 * Instrumented.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 */
public final class Instrumented<K extends Bytes, V extends Bytes>
    implements Evicted<K, V> {
    /**
     * Evicted.
     */
    private final Evicted<K, V> origin;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     *
     * @param evicted The evicted
     * @param statistics The statistics
     */
    public Instrumented(
        final Evicted<K, V> evicted,
        final Statistics statistics
    ) {
        this.origin = evicted;
        this.stats = statistics;
    }

    @Override
    public void add(final Entry<K, V> entry) {
        this.stats.statistic("evictions").increment(1);
        this.origin.add(entry);
    }

    @Override
    public Entry<K, V> entry(final int index) {
        return this.origin.entry(index);
    }

    @Override
    public int count() {
        return this.origin.count();
    }

    @Override
    public void clear() {
        this.stats.statistic("evictions").reset();
        this.origin.clear();
    }
}
