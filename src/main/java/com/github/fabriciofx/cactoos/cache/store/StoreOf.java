/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.store;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.entries.EntriesOf;
import com.github.fabriciofx.cactoos.cache.entry.InvalidEntry;
import com.github.fabriciofx.cactoos.cache.keys.KeysOf;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.cactoos.Bytes;

/**
 * StoreOf.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class StoreOf<K extends Bytes, V> implements Store<K, V> {
    /**
     * Entries.
     */
    private final Map<Key<K>, Entry<K, V>> records;

    /**
     * Ctor.
     */
    public StoreOf() {
        this(new ConcurrentHashMap<>());
    }

    /**
     * Ctor.
     * @param entries The entries
     */
    public StoreOf(final Map<Key<K>, Entry<K, V>> entries) {
        this.records = entries;
    }

    @Override
    public Entry<K, V> retrieve(final Key<K> key) {
        return this.records.getOrDefault(key, new InvalidEntry<>());
    }

    @Override
    public Entry<K, V> save(final Key<K> key, final Entry<K, V> entry) {
        return Objects.requireNonNullElseGet(
            this.records.put(key, entry),
            InvalidEntry::new
        );
    }

    @Override
    public Entry<K, V> delete(final Key<K> key) {
        return Objects.requireNonNullElseGet(
            this.records.remove(key),
            InvalidEntry::new
        );
    }

    @Override
    public boolean contains(final Key<K> key) {
        return this.records.containsKey(key);
    }

    @Override
    public Keys<K> keys() {
        return new KeysOf<>(this.records.keySet());
    }

    @Override
    public Entries<K, V> entries() {
        return new EntriesOf<>(this.records);
    }
}
