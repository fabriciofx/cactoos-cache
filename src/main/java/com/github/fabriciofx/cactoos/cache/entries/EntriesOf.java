/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entries;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Invalidate;
import com.github.fabriciofx.cactoos.cache.Key;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.cactoos.Bytes;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.number.SumOf;

/**
 * EntriesOf.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class EntriesOf<K extends Bytes, V extends Bytes>
    implements Entries<K, V> {
    /**
     * Entries.
     */
    private final Map<Key<K>, Entry<K, V>> entries;

    /**
     * Ctor.
     *
     * @param entries The entries
     */
    public EntriesOf(final Map<Key<K>, Entry<K, V>> entries) {
        this.entries = entries;
    }

    @Override
    public int count() {
        return this.entries.size();
    }

    @Override
    public List<Entry<K, V>> invalidate(final Invalidate<K, V> invalidate) {
        return invalidate.apply(this.entries);
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    @Override
    public int size() {
        return new SumOf(
            new Joined<Integer>(
                new Mapped<>(Key::size, this.entries.keySet()),
                new Mapped<>(Entry::size, this.entries.values())
            )
        ).intValue();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return this.entries.values().iterator();
    }
}
