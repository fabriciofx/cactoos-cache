/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.key;

import com.github.fabriciofx.cactoos.cache.Hash;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.hash.Murmur3Hash;
import org.cactoos.Bytes;

/**
 * KeyOf.
 * @param <K> the key value type
 * @since 0.0.1
 */
public final class KeyOf<K extends Bytes> implements Key<K> {
    /**
     * Value.
     */
    private final K val;

    /**
     * Hash.
     */
    private final Hash<?> hsh;

    /**
     * Ctor.
     * @param value A value
     */
    public KeyOf(final K value) {
        this(value, new Murmur3Hash(value));
    }

    /**
     * Ctor.
     * @param value A value
     * @param hash The hash algorithm
     */
    public KeyOf(final K value, final Hash<?> hash) {
        this.val = value;
        this.hsh = hash;
    }

    @Override
    public K value() {
        return this.val;
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
        return this.hsh.asInt();
    }
}
