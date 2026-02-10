/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.store;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Policies;
import com.github.fabriciofx.cactoos.cache.Store;
import org.cactoos.Bytes;

/**
 * Policed.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.9
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class Policed<K extends Bytes, V extends Bytes>
    implements Store<K, V> {
    /**
     * Cache.
     */
    private final Cache<K, V> cache;

    /**
     * Policies.
     */
    private final Policies<K, V> policies;

    /**
     * Ctor.
     * @param cache The cache
     * @param policies The policies
     */
    public Policed(final Cache<K, V> cache, final Policies<K, V> policies) {
        this.cache = cache;
        this.policies = policies;
    }

    @Override
    public Entry<K, V> retrieve(final Key<K> key) {
        this.policies.apply(this.cache);
        return this.cache.store().retrieve(key);
    }

    @Override
    public Entry<K, V> save(final Key<K> key, final Entry<K, V> entry) {
        final Entry<K, V> replaced = this.cache.store().save(key, entry);
        this.policies.apply(this.cache);
        return replaced;
    }

    @Override
    public Entry<K, V> delete(final Key<K> key) {
        this.policies.apply(this.cache);
        return this.cache.store().delete(key);
    }

    @Override
    public boolean contains(final Key<K> key) {
        this.policies.apply(this.cache);
        return this.cache.store().contains(key);
    }

    @Override
    public Keys<K> keys() {
        this.policies.apply(this.cache);
        return this.cache.store().keys();
    }

    @Override
    public Entries<K, V> entries() {
        this.policies.apply(this.cache);
        return this.cache.store().entries();
    }
}
