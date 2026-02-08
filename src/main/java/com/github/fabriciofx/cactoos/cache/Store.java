/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Store.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Store<K extends Bytes, V extends Bytes> {
    /**
     * Retrieve an entry from store.
     *
     * @param key The key
     * @return The entry associated with the key or an invalid entry otherwise
     */
    Entry<K, V> retrieve(Key<K> key);

    /**
     * Save an entry into store.
     *
     * @param key The key associated to the entry
     * @param entry An entry
     * @return The previous entry associated with the key or an invalid entry
     *  otherwise
     */
    Entry<K, V> save(Key<K> key, Entry<K, V> entry);

    /**
     * Delete an entry into store.
     *
     * @param key The key associated to the entry
     * @return The entry associated with the key or an invalid entry otherwise
     */
    Entry<K, V> delete(Key<K> key);

    /**
     * Checks if the store has an entry associated with the key.
     *
     * @param key The key
     * @return True if there is, false otherwise
     */
    boolean contains(Key<K> key);

    /**
     * Retrieve the keys.
     *
     * @return The keys
     */
    Keys<K> keys();

    /**
     * Retrieve entries.
     *
     * @return The entries
     */
    Entries<K, V> entries();
}
