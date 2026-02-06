/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

/**
 * Hash.
 * @param <T> the hash value type
 * @since 0.0.10
 */
public interface Hash<T> {
    /**
     * Retrieve the hash value.
     * @return The hash value
     */
    T value();

    /**
     * Retrieve the hash value as a hexadecimal string.
     * @return The hash value as a hexadecimal string
     */
    String asString();

    /**
    * Retrieve the hash value as an integer.
    * @return The hash value as an integer
    */
    int asInt();
}
