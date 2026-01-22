/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import org.cactoos.Func;

/**
 * Policy.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
@FunctionalInterface
public interface Policy<K, V> extends Func<Store<K, V>, List<Entry<K, V>>> {
}
