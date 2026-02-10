/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Evicted.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 */
public interface Evicted<K extends Bytes, V extends Bytes> {
    /**
     * Add an entry.
     * @param entry The entry
     */
    void add(Entry<K, V> entry);

    /**
     * Get an evicted entry by index.
     * @param index The index
     * @return The evicted entry
     */
    Entry<K, V> entry(int index);

    /**
     * Count of evicted entries.
     * @return Count of evicted entries
     */
    int count();

    /**
     * Clear evicted entries.
     */
    void clear();
}
