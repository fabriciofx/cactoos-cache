/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.util.List;
import java.util.Set;

/**
 * Metadata.
 *
 * @since 0.0.7
 */
public interface Metadata {
    /**
     * Names of metadata items.
     * @return A set of names
     */
    Set<String> names();

    /**
     * Value of a metadata item by its name and type.
     * @param name Metadata item name
     * @param type Metadata item type
     * @return The value
     * @param <T> Type of the value
     */
    <T> T value(String name, Class<T> type);

    /**
     * Adds a metadata item and returns a new Metadata instance.
     * @param name Metadata item name
     * @param value Metadata item value
     * @return A new Metadata instance
     * @param <T> Type of the value
     */
    <T> Metadata with(String name, T value);

    /**
     * Checks if the metadata contains any given values.
     * @param values The values to check
     * @return True if any values are present, false otherwise
     * @param <T> Type of the value
     */
    <T> boolean contains(List<T> values);
}
