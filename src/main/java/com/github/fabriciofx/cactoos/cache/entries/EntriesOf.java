/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entries;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cactoos.set.SetOf;

/**
 * EntriesOf.
 *
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class EntriesOf<D, V> implements Entries<D, V> {
    /**
     * Entries.
     */
    private final Map<Key<D>, Entry<D, V>> entries;

    /**
     * Ctor.
     *
     * @param entries The entries
     */
    public EntriesOf(final Map<Key<D>, Entry<D, V>> entries) {
        this.entries = entries;
    }

    @Override
    public int count() {
        return this.entries.size();
    }

    @Override
    public List<Entry<D, V>> invalidate(final Iterable<String> metadata) {
        final Set<String> lookup = new SetOf<>(metadata);
        return this.entries.values()
            .stream()
            .filter(
                entry -> entry.metadata().values().stream()
                    .flatMap(List::stream)
                    .anyMatch(lookup::contains)
            )
            .toList();
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    @Override
    public Iterator<Entry<D, V>> iterator() {
        return this.entries.values().iterator();
    }
}
