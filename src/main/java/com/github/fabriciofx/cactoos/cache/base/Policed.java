/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Enforcer;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.enforcer.DelayedEnforcer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * Cache with policies enforcement.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
public final class Policed<K extends Bytes, V> implements Cache<K, V> {
    /**
     * Cache.
     */
    private final Cache<K, V> origin;

    /**
     * Enforcer.
     */
    private final Enforcer<K, V> enforcer;

    /**
     * Policies.
     */
    private final List<Policy<K, V>> policies;

    /**
     * Ctor.
     * @param cache The cache
     * @param policies The policies
     */
    @SafeVarargs
    public Policed(
        final Cache<K, V> cache,
        final Policy<K, V>... policies
    ) {
        this(
            cache,
            new DelayedEnforcer<>(500, TimeUnit.MILLISECONDS),
            new ListOf<>(policies)
        );
    }

    /**
     * Ctor.
     * @param cache The cache
     * @param enforcer The enforcer
     * @param policies The policies
     */
    @SafeVarargs
    public Policed(
        final Cache<K, V> cache,
        final Enforcer<K, V> enforcer,
        final Policy<K, V>... policies
    ) {
        this(cache, enforcer, new ListOf<>(policies));
    }

    /**
     * Ctor.
     * @param cache The cache
     * @param enforcer The enforcer
     * @param policies The policies
     */
    public Policed(
        final Cache<K, V> cache,
        final Enforcer<K, V> enforcer,
        final List<Policy<K, V>> policies
    ) {
        this.origin = cache;
        this.enforcer = enforcer;
        this.policies = policies;
    }

    @Override
    public Store<K, V> store() {
        return new com.github.fabriciofx.cactoos.cache.store.Policed<>(
            this.origin,
            this.enforcer,
            this.policies
        );
    }

    @Override
    public Statistics statistics() {
        this.enforcer.apply(this.origin, this.policies);
        return this.origin.statistics();
    }

    @Override
    public List<Entry<K, V>> evicted() {
        return this.enforcer.apply(this.origin, this.policies);
    }

    @Override
    public void clear() {
        this.origin.clear();
    }
}
