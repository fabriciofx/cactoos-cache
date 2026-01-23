/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Cache.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Cache<K extends Bytes, V> {
    /**
     * Store.
     * @return The store.
     */
    Store<K, V> store();

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
