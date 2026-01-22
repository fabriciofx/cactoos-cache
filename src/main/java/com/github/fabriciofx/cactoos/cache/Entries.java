/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;

/**
 * Entries.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Entries<K, V> extends Iterable<Entry<K, V>> {
    /**
     * Retrieve the amount of entries.
     * @return The amount of entries.
     */
    int count();

    /**
     * Invalidate entries according metadata.
     * @param metadata The metadata
     * @return The invalidated entries associated with this metadata
     */
    List<Entry<K, V>> invalidate(Iterable<String> metadata);

    /**
     * Clear entries.
     */
    void clear();
}
