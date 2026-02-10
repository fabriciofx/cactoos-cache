/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Evicted;
import com.github.fabriciofx.cactoos.cache.Policies;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.policies.DelayedPolicies;
import java.util.concurrent.TimeUnit;
import org.cactoos.Bytes;

/**
 * Cache with policies enforcement.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
public final class Policed<K extends Bytes, V extends Bytes>
    implements Cache<K, V> {
    /**
     * Cache.
     */
    private final Cache<K, V> origin;

    /**
     * Policies.
     */
    private final Policies<K, V> policies;

    /**
     * Ctor.
     * @param cache The cache
     * @param policies The policies
     */
    @SafeVarargs
    public Policed(final Cache<K, V> cache, final Policy<K, V>... policies) {
        this(
            cache,
            new DelayedPolicies<>(500, TimeUnit.MILLISECONDS, policies)
        );
    }

    /**
     * Ctor.
     * @param cache The cache
     * @param policies The policies
     */
    public Policed(final Cache<K, V> cache, final Policies<K, V> policies) {
        this.origin = cache;
        this.policies = policies;
    }

    @Override
    public Store<K, V> store() {
        return new com.github.fabriciofx.cactoos.cache.store.Policed<>(
            this.origin,
            this.policies
        );
    }

    @Override
    public Statistics statistics() {
        this.policies.apply(this.origin);
        return this.origin.statistics();
    }

    @Override
    public Evicted<K, V> evicted() {
        this.policies.apply(this.origin);
        return this.origin.evicted();
    }

    @Override
    public void clear() {
        this.origin.clear();
    }

    @Override
    public int size() {
        return this.origin.size();
    }
}
