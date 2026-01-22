/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import org.cactoos.Func;

/**
 * Policy.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 */
@FunctionalInterface
public interface Policy<D, V> extends Func<Store<D, V>, List<Entry<D, V>>> {
}
