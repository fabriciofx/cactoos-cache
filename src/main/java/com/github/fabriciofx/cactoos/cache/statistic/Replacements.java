/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.statistic;

import com.github.fabriciofx.cactoos.cache.Statistic;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Replacements.
 * <p>Number of times the entry was replaced by new save.</p>
 * @since 0.0.9
 */
public final class Replacements implements Statistic {
    /**
     * Count.
     */
    private final AtomicInteger count;

    /**
     * Ctor.
     */
    public Replacements() {
        this(new AtomicInteger(0));
    }

    /**
     * Ctor.
     * @param count The count
     */
    public Replacements(final AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String name() {
        return "replacements";
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
