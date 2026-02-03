/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import java.util.Map;
import org.cactoos.Bytes;
import org.cactoos.Func;

/**
 * Invalidate cache entries.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
@FunctionalInterface
public interface Invalidate<K extends Bytes, V> extends
    Func<Map<Key<K>, Entry<K, V>>, List<Entry<K, V>>> {
}
