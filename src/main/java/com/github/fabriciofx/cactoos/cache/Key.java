/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import org.cactoos.Bytes;

/**
 * Key.
 * <p>You MUST implement equals() and hashCode() in Key's implementation class.
 * @param <K> the key value type
 * @since 0.0.1
 */
public interface Key<K extends Bytes> {
    /**
     * Retrieve the value associated with this key.
     * @return The value
     */
    K value();

    /**
     * Retrieve the hash of this key.
     * @return The key's hash
     */
    Hash<?> hash();

    /**
     * Size of the key in bytes.
     * @return The size
     */
    int size();
}
