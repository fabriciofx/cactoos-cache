/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.statistic;

import com.github.fabriciofx.cactoos.cache.Statistic;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Misses.
 * <p>Number of times the value was not found.</p>
 * @since 0.0.1
 */
public final class Misses implements Statistic {
    /**
     * Count.
     */
    private final AtomicInteger count;

    /**
     * Ctor.
     */
    public Misses() {
        this(new AtomicInteger(0));
    }

    /**
     * Ctor.
     * @param count The count
     */
    public Misses(final AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String name() {
        return "misses";
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
