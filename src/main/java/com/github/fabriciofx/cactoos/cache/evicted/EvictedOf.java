/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.evicted;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Evicted;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * EvictedOf.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 */
public final class EvictedOf<K extends Bytes, V extends Bytes>
    implements Evicted<K, V> {
    /**
     * Items.
     */
    private final List<Entry<K, V>> items;

    /**
     * Ctor.
     */
    public EvictedOf() {
        this(new ListOf<>());
    }

    /**
     * Ctor.
     * @param items Items
     */
    public EvictedOf(final List<Entry<K, V>> items) {
        this.items = items;
    }

    @Override
    public void add(final Entry<K, V> entry) {
        this.items.add(entry);
    }

    @Override
    public Entry<K, V> entry(final int index) {
        return this.items.get(index);
    }

    @Override
    public int count() {
        return this.items.size();
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
