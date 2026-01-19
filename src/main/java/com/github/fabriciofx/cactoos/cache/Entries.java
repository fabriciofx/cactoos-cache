/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;

/**
 * Entries.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Entries<D, V> extends Iterable<Entry<D, V>> {
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
    List<Entry<D, V>> invalidate(Iterable<String> metadata);

    /**
     * Clear entries.
     */
    void clear();
}
