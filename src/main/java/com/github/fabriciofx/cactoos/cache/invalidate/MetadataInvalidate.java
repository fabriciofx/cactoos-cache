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
 * MetadataInvalidate.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class MetadataInvalidate<K extends Bytes, V extends Bytes>
    implements Invalidate<K, V> {
    /**
     * Values.
     */
    private final Iterable<?> values;

    /**
     * Ctor.
     * @param values The metadata values
     */
    public MetadataInvalidate(final Iterable<?> values) {
        this.values = values;
    }

    @Override
    public List<Entry<K, V>> apply(final Map<Key<K>, Entry<K, V>> input) {
        final List<Entry<K, V>> invalidated = new ListOf<>();
        input.values().forEach(
            entry -> {
                final Metadata metadata = entry.metadata();
                if (metadata.hasAny(this.values)) {
                    invalidated.add(input.remove(entry.key()));
                }
            }
        );
        return invalidated;
    }
}
