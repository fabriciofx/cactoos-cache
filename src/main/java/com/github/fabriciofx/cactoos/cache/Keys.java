/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

/**
 * Keys.
 * @param <D> the key domain type
 * @since 0.0.1
 */
public interface Keys<D> extends Iterable<Key<D>> {
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
