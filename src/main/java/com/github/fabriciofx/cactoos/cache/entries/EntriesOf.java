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

/**
 * EntriesOf.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.3
 */
public final class EntriesOf<K extends Bytes, V> implements Entries<K, V> {
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
    public List<Entry<K, V>> invalidate(final Invalidate<K, V> invalidate)
        throws Exception {
        return invalidate.apply(this.entries);
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return this.entries.values().iterator();
    }
}
