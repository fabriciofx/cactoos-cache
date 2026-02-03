/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.metadata;

import com.github.fabriciofx.cactoos.cache.Metadata;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;

/**
 * Metadata.
 *
 * @since 0.0.7
 */
public final class MetadataOf implements Metadata {
    /**
     * Items.
     */
    private final Map<String, Object> items;

    /**
     * Ctor.
     */
    public MetadataOf() {
        this(new HashMap<>());
    }

    /**
     * Ctor.
     *
     * @param items Metadata items
     */
    @SafeVarargs
    public MetadataOf(final MapEntry<String, Object>... items) {
        this(new MapOf<>(items));
    }

    /**
     * Ctor.
     *
     * @param items Metadata items
     */
    public MetadataOf(final Map<String, Object> items) {
        this.items = items;
    }

    @Override
    public Set<String> names() {
        return this.items.keySet();
    }

    @Override
    public <T> T value(final String name, final Class<T> type) {
        return type.cast(this.items.get(name));
    }

    @Override
    public <T> Metadata with(final String name, final T value) {
        final Map<String, Object> map = new HashMap<>(this.items);
        map.put(name, value);
        return new MetadataOf(map);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> boolean contains(final List<T> values) {
        return this.items.values().stream()
            .flatMap(
                value ->
                    Stream.concat(
                        Stream.of(value),
                        Stream.of(value)
                            .filter(Collection.class::isInstance)
                            .map(collection -> (Collection<T>) collection)
                            .flatMap(Collection::stream)
                    )
            )
            .anyMatch(values::contains);
    }
}
