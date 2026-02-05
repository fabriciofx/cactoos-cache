/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.statistic;

import com.github.fabriciofx.cactoos.cache.Statistic;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Inserts.
 * <p>Number of times a new entry has been inserted in the cache.</p>
 * @since 0.0.9
 */
public final class Insertions implements Statistic {
    /**
     * Count.
     */
    private final AtomicInteger count;

    /**
     * Ctor.
     */
    public Insertions() {
        this(new AtomicInteger(0));
    }

    /**
     * Ctor.
     * @param count The count
     */
    public Insertions(final AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String name() {
        return "insertions";
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
