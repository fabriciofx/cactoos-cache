/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;

/**
 * Store.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public interface Store<D, V> {
    /**
     * Retrieve an entry from store.
     * @param key The key
     * @return The entry associated with the key or an invalid entry otherwise
     */
    Entry<D, V> retrieve(Key<D> key);

    /**
     * Save an entry into store.
     * @param key The key associated to the entry
     * @param entry An entry
     * @return Elements removed automatically
     * @throws Exception If something goes wrong
     */
    List<Entry<D, V>> save(Key<D> key, Entry<D, V> entry) throws Exception;

    /**
     * Delete an entry into store.
     * @param key The key associated to the entry
     * @return The entry associated with the key or an invalid entry otherwise
     */
    Entry<D, V> delete(Key<D> key);

    /**
     * Checks if the store has an entry associated with the key.
     * @param key The key
     * @return True if there is, false otherwise
     */
    boolean contains(Key<D> key);

    /**
     * Retrieve the keys.
     * @return The keys
     */
    Keys<D> keys();

    /**
     * Retrieve entries.
     * @return The entries
     */
    Entries<D, V> entries();
}
