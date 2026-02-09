/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policy;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * MaxCountPolicy.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.12
 */
public final class MaxCountPolicy<K extends Bytes, V extends Bytes>
    implements Policy<K, V> {
    /**
     * Max count.
     */
    private final int max;

    /**
     * Ctor.
     */
    public MaxCountPolicy() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     *
     * @param max Maximum size
     */
    public MaxCountPolicy(final int max) {
        this.max = max;
    }

    @Override
    public List<Entry<K, V>> apply(final Store<K, V> store) {
        final List<Entry<K, V>> evicted = new ListOf<>();
        final Entries<K, V> entries = store.entries();
        while (entries.count() > this.max) {
            evicted.add(store.delete(store.keys().iterator().next()));
        }
        return evicted;
    }
}
