/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import org.cactoos.BiFunc;
import org.cactoos.Bytes;

/**
 * Enforcer of policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
@FunctionalInterface
public interface Enforcer<K extends Bytes, V>
    extends BiFunc<Cache<K, V>, List<Policy<K, V>>, List<Entry<K, V>>> {
}
