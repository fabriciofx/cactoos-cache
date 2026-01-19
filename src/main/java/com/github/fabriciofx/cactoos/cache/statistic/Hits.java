/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.statistic;

import com.github.fabriciofx.cactoos.cache.Statistic;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hits.
 * <p>Number of times the value was found in the cache.</p>
 * @since 0.0.1
 */
public final class Hits implements Statistic {
    /**
     * Count.
     */
    private final AtomicInteger count;

    /**
     * Ctor.
     */
    public Hits() {
        this(new AtomicInteger(0));
    }

    /**
     * Ctor.
     * @param count Count
     */
    public Hits(final AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String name() {
        return "hits";
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
