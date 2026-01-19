/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.keys;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Statistics;
import java.util.Iterator;

/**
 * Instrumented Keys.
 * @param <D> the key domain type
 * @since 0.0.1
 */
public final class Instrumented<D> implements Keys<D> {
    /**
     * Keys.
     */
    private final Keys<D> origin;

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
        final Keys<D> keys,
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
    public Iterator<Key<D>> iterator() {
        return this.origin.iterator();
    }
}
