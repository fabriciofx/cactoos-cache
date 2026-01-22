/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import java.util.Map;

/**
 * Entry.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Entry<K, V> {
    /**
     * Retrieve the key associated with this entry.
     * @return The key
     */
    Key<K> key();

    /**
     * Retrieve the value associated with this entry.
     * @return The value
     */
    V value();

    /**
     * Retrieve the metadata associated with this entry.
     * @return The metadata
     */
    Map<String, List<String>> metadata();

    /**
     * Checks if an entry is valid.
     * @return True if an entry is valid, false otherwise
     */
    boolean valid();
}
