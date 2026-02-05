/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import java.util.Map;
import org.cactoos.Bytes;

/**
 * Invalidate cache entries.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
@FunctionalInterface
public interface Invalidate<K extends Bytes, V> {
    List<Entry<K, V>> apply(Map<Key<K>, Entry<K, V>> entries);
}
