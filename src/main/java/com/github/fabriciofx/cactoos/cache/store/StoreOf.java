/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.store;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.entries.EntriesOf;
import com.github.fabriciofx.cactoos.cache.entry.InvalidEntry;
import com.github.fabriciofx.cactoos.cache.keys.KeysOf;
import com.github.fabriciofx.cactoos.cache.policy.MaxSizePolicy;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StoreOf.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class StoreOf<D, V> implements Store<D, V> {
    /**
     * Entries.
     */
    private final Map<Key<D>, Entry<D, V>> records;

    /**
     * Policy.
     */
    private final Policy<D, V> policy;

    /**
     * Ctor.
     */
    public StoreOf() {
        this(new MaxSizePolicy<>());
    }

    /**
     * Ctor.
     * @param policy The policy
     */
    public StoreOf(final Policy<D, V> policy) {
        this(new ConcurrentHashMap<>(), policy);
    }

    /**
     * Ctor.
     * @param entries The entries
     * @param policy The policy
     */
    public StoreOf(
        final Map<Key<D>, Entry<D, V>> entries,
        final Policy<D, V> policy
    ) {
        this.records = entries;
        this.policy = policy;
    }

    @Override
    public Entry<D, V> retrieve(final Key<D> key) {
        return this.records.getOrDefault(key, new InvalidEntry<>());
    }

    @Override
    public List<Entry<D, V>> save(final Key<D> key, final Entry<D, V> entry)
        throws Exception {
        final List<Entry<D, V>> evicted = this.policy.apply(this);
        final Entry<D, V> removed = this.records.put(key, entry);
        if (removed != null) {
            evicted.add(removed);
        }
        return evicted;
    }

    @Override
    public Entry<D, V> delete(final Key<D> key) {
        return Objects.requireNonNullElseGet(
            this.records.remove(key),
            InvalidEntry::new
        );
    }

    @Override
    public boolean contains(final Key<D> key) {
        return this.records.containsKey(key);
    }

    @Override
    public Keys<D> keys() {
        return new KeysOf<>(this.records.keySet());
    }

    @Override
    public Entries<D, V> entries() {
        return new EntriesOf<>(this.records);
    }
}
