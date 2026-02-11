/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 */
public interface Policies<K extends Bytes, V extends Bytes>
    extends AutoCloseable {
    /**
    * Apply policies to the cache.
    * @param cache Cache to apply policies
    */
    void apply(Cache<K, V> cache);

    @Override
    void close();
}
