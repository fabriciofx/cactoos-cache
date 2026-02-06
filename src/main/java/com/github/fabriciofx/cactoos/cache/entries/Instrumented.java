/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entries;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Invalidate;
import com.github.fabriciofx.cactoos.cache.Statistics;
import java.util.Iterator;
import java.util.List;
import org.cactoos.Bytes;

/**
 * Instrumented Entries.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public final class Instrumented<K extends Bytes, V> implements Entries<K, V> {
    /**
     * Entries.
     */
    private final Entries<K, V> origin;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     * @param entries The entries
     * @param statistics The statistics
     */
    public Instrumented(
        final Entries<K, V> entries,
        final Statistics statistics
    ) {
        this.origin = entries;
        this.stats = statistics;
    }

    @Override
    public int count() {
        return this.origin.count();
    }

    @Override
    public List<Entry<K, V>> invalidate(final Invalidate<K, V> invalidate) {
        final List<Entry<K, V>> invalidated = this.origin.invalidate(
            invalidate
        );
        this.stats.statistic("invalidations").increment(invalidated.size());
        return invalidated;
    }

    @Override
    public void clear() {
        this.origin.clear();
        this.stats.statistic("invalidations").increment(this.count());
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return this.origin.iterator();
    }
}
