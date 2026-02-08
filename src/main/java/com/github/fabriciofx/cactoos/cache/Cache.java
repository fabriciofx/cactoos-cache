/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import org.cactoos.Bytes;

/**
 * Cache.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Cache<K extends Bytes, V extends Bytes> {
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
     * Evicted entries.
     * @return The evicted entries
     */
    List<Entry<K, V>> evicted();

    /**
     * Clear the cache.
     */
    void clear();

    /**
     * Size of the cache in bytes.
     * @return The size
     */
    int size();
}
