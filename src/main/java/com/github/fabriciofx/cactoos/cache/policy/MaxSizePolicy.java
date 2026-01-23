/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policy;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.Bytes;

/**
 * MaxSizePolicy.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 */
public final class MaxSizePolicy<K extends Bytes, V> implements Policy<K, V> {
    /**
     * Max size.
     */
    private final int max;

    /**
     * Ctor.
     */
    public MaxSizePolicy() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     *
     * @param max Maximum size
     */
    public MaxSizePolicy(final int max) {
        this.max = max;
    }

    @Override
    public List<Entry<K, V>> apply(final Store<K, V> store) throws Exception {
        final List<Entry<K, V>> evicted = new LinkedList<>();
        final Entries<K, V> entries = store.entries();
        while (entries.count() >= this.max) {
            evicted.add(store.delete(store.keys().iterator().next()));
        }
        return evicted;
    }
}
