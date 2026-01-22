/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.key;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.hash.Murmur3Hash;
import org.cactoos.Bytes;
import org.cactoos.text.HexOf;
import org.cactoos.text.Sticky;
import org.cactoos.text.UncheckedText;

/**
 * KeyOf.
 * @param <D> the key domain type
 * @since 0.0.1
 */
public final class KeyOf<D> implements Key<D> {
    /**
     * Value.
     */
    private final D value;

    /**
     * Hash.
     */
    private final UncheckedText hsh;

    /**
     * Ctor.
     * @param value A value
     * @param bytes A value as bytes
     */
    public KeyOf(final D value, final Bytes bytes) {
        this(
            value,
            new UncheckedText(
                new Sticky(
                    new HexOf(
                        new Murmur3Hash(bytes)
                    )
                )
            )
        );
    }

    /**
     * Ctor.
     * @param value A value
     * @param hash The value's hash
     */
    public KeyOf(final D value, final UncheckedText hash) {
        this.value = value;
        this.hsh = hash;
    }

    @Override
    public D domain() {
        return this.value;
    }

    @Override
    public String hash() {
        return this.hsh.asString();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other
            || other instanceof Key
            && this.hash().equals(Key.class.cast(other).hash());
    }

    @Override
    public int hashCode() {
        return this.hash().hashCode();
    }
}
