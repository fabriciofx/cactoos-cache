/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Keys.
 * @param <K> the key value type
 * @since 0.0.1
 */
public interface Keys<K extends Bytes> extends Iterable<Key<K>> {
    /**
     * Retrieve the amount of keys.
     * @return The amount of keys.
     */
    int count();

    /**
     * Clear keys.
     */
    void clear();
}
