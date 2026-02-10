/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policies;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Policies;
import com.github.fabriciofx.cactoos.cache.Policy;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * Immediate policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 */
public final class ImmediatePolicies<K extends Bytes, V extends Bytes>
    implements Policies<K, V> {
    /**
     * Items.
     */
    private final List<Policy<K, V>> items;

    /**
     * Ctor.
     *
     * @param items Items
     */
    @SafeVarargs
    public ImmediatePolicies(final Policy<K, V>... items) {
        this(new ListOf<>(items));
    }

    /**
    * Ctor.
    *
    * @param items Items
    */
    public ImmediatePolicies(final List<Policy<K, V>> items) {
        this.items = items;
    }

    @Override
    public void apply(final Cache<K, V> cache) {
        this.items.forEach(policy -> policy.apply(cache));
    }
}
