/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Entry.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Entry<K extends Bytes, V extends Bytes> {
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
    Metadata metadata();

    /**
     * Checks if an entry is valid.
     * @return True if an entry is valid, false otherwise
     */
    boolean valid();

    /**
     * Size of the entry in bytes.
     * @return The size
     */
    int size();
}
