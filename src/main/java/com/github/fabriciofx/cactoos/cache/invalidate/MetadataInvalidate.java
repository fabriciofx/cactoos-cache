/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.invalidate;

import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Invalidate;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Metadata;
import java.util.List;
import java.util.Map;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * MetadataContains.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class MetadataInvalidate<K extends Bytes, V>
    implements Invalidate<K, V> {
    /**
     * Values.
     */
    private final List<Object> values;

    /**
     * Ctor.
     * @param values The values
     */
    public MetadataInvalidate(final List<Object> values) {
        this.values = values;
    }

    @Override
    public List<Entry<K, V>> apply(final Map<Key<K>, Entry<K, V>> input)
        throws Exception {
        final List<Entry<K, V>> invalidated = new ListOf<>();
        input.values().forEach(
            entry -> {
                final Metadata metadata = entry.metadata();
                if (metadata.contains(this.values)) {
                    invalidated.add(input.remove(entry.key()));
                }
            }
        );
        return invalidated;
    }
}
