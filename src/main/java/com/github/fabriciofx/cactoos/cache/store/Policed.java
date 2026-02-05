/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.store;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Enforcer;
import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import java.util.List;
import org.cactoos.Bytes;

/**
 * Policed.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.9
 */
public final class Policed<K extends Bytes, V> implements Store<K, V> {
    /**
     * Cache.
     */
    private final Cache<K, V> cache;

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
     * @param enforcer The enforcer
     * @param policies The policies
     */
    public Policed(
        final Cache<K, V> cache,
        final Enforcer<K, V> enforcer,
        final List<Policy<K, V>> policies
    ) {
        this.cache = cache;
        this.enforcer = enforcer;
        this.policies = policies;
    }

    @Override
    public Entry<K, V> retrieve(final Key<K> key) {
        this.enforcer.apply(this.cache, this.policies);
        return this.cache.store().retrieve(key);
    }

    @Override
    public Entry<K, V> save(final Key<K> key, final Entry<K, V> entry) {
        this.enforcer.apply(this.cache, this.policies);
        return this.cache.store().save(key, entry);
    }

    @Override
    public Entry<K, V> delete(final Key<K> key) {
        this.enforcer.apply(this.cache, this.policies);
        return this.cache.store().delete(key);
    }

    @Override
    public boolean contains(final Key<K> key) {
        this.enforcer.apply(this.cache, this.policies);
        return this.cache.store().contains(key);
    }

    @Override
    public Keys<K> keys() {
        this.enforcer.apply(this.cache, this.policies);
        return this.cache.store().keys();
    }

    @Override
    public Entries<K, V> entries() {
        this.enforcer.apply(this.cache, this.policies);
        return this.cache.store().entries();
    }
}
