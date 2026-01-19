/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

/**
 * Statistics.
 * @since 0.0.1
 */
public interface Statistics extends Iterable<Statistic> {
    /**
     * Retrieve a statistic.
     * @param name The name of statistic
     * @return The statistic
     */
    Statistic statistic(String name);

    /**
     * Reset all statistics.
     */
    void reset();
}
