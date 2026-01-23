/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.keys;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Statistics;
import java.util.Iterator;
import org.cactoos.Bytes;

/**
 * Instrumented Keys.
 * @param <K> the key value type
 * @since 0.0.1
 */
public final class Instrumented<K extends Bytes> implements Keys<K> {
    /**
     * Keys.
     */
    private final Keys<K> origin;

    /**
     * Statistics.
     */
    private final Statistics stats;

    /**
     * Ctor.
     * @param keys The keys
     * @param statistics The statistics
     */
    public Instrumented(
        final Keys<K> keys,
        final Statistics statistics
    ) {
        this.origin = keys;
        this.stats = statistics;
    }

    @Override
    public int count() {
        return this.origin.count();
    }

    @Override
    public void clear() {
        this.origin.clear();
        this.stats.statistic("invalidations").increment(this.count());
    }

    @Override
    public Iterator<Key<K>> iterator() {
        return this.origin.iterator();
    }
}
