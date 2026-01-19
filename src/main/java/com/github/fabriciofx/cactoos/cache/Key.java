/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

/**
 * Key.
 * <p>You MUST implement equals() and hashCode() in Key's implementation class.
 * @param <D> the key domain type
 * @since 0.0.1
 */
public interface Key<D> {
    /**
     * Retrieve the domain associated with this key.
     * @return The domain
     */
    D domain();

    /**
     * Calculate the key's hash.
     * @return The key's hash
     */
    String hash();
}
