/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.statistic;

import com.github.fabriciofx.cactoos.cache.Statistic;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Invalidation.
 * <p>Explicit removals.</p>
 * @since 0.0.1
 */
public final class Invalidations implements Statistic {
    /**
     * Count.
     */
    private final AtomicInteger count;

    /**
     * Ctor.
     */
    public Invalidations() {
        this(new AtomicInteger(0));
    }

    /**
     * Ctor.
     * @param count Count
     */
    public Invalidations(final AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String name() {
        return "invalidations";
    }

    @Override
    public void increment(final int num) {
        this.count.addAndGet(num);
    }

    @Override
    public void reset() {
        this.count.set(0);
    }

    @Override
    public int value() {
        return this.count.get();
    }
}
