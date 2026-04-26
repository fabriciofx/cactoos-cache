/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Policy.
 * @param <K> The key value type
 * @param <V> The entry value type
 * @since 0.0.1
 */
@FunctionalInterface
public interface Policy<K extends Bytes, V extends Bytes> {

    void apply(Cache<K, V> cache);
}
