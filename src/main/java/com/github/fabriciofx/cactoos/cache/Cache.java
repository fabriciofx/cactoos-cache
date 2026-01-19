/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

/**
 * Cache.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Cache<D, V> {
    /**
     * Store.
     * @return The store.
     */
    Store<D, V> store();

    /**
     * Statistics.
     * @return The statistics
     */
    Statistics statistics();

    /**
     * Clear the cache.
     */
    void clear();
}
