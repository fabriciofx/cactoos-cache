/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import org.cactoos.Bytes;

/**
 * Entries.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Entries<K extends Bytes, V> extends Iterable<Entry<K, V>> {
    /**
     * Retrieve the amount of entries.
     * @return The amount of entries.
     */
    int count();

    /**
     * Invalidate entries.
     * @param invalidate The invalidate function
     * @return The invalidated entries
     * @throws Exception If something goes wrong
     */
    List<Entry<K, V>> invalidate(Invalidate<K, V> invalidate) throws Exception;

    /**
     * Clear entries.
     */
    void clear();
}
