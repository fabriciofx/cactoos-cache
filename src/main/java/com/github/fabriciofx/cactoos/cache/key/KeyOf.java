/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.key;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.hash.Murmur3Hash;
import org.cactoos.Bytes;
import org.cactoos.scalar.Unchecked;

/**
 * KeyOf.
 * @param <K> the key value type
 * @since 0.0.1
 */
public final class KeyOf<K extends Bytes> implements Key<K> {
    /**
     * Hex chars.
     */
    private static final char[] HEX_CHARS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    /**
     * Value.
     */
    private final K val;

    /**
     * Hash.
     */
    private final Unchecked<long[]> hsh;

    /**
     * Ctor.
     * @param value A value
     */
    public KeyOf(final K value) {
        this(
            value,
            new Unchecked<>(
                new Murmur3Hash(value)
            )
        );
    }

    /**
     * Ctor.
     * @param value A value
     * @param hash The value's hash
     */
    public KeyOf(final K value, final Unchecked<long[]> hash) {
        this.val = value;
        this.hsh = hash;
    }

    @Override
    public K value() {
        return this.val;
    }

    @Override
    public String hash() {
        final long[] hash = this.hsh.value();
        final char[] hex = new char[32];
        for (int idx = 0; idx < 8; ++idx) {
            final int value = (int) (hash[0] >>> (idx * 8)) & 0xFF;
            hex[idx * 2] = KeyOf.HEX_CHARS[value >>> 4];
            hex[idx * 2 + 1] = KeyOf.HEX_CHARS[value & 0x0F];
        }
        for (int idx = 0; idx < 8; ++idx) {
            final int value = (int) (hash[1] >>> (idx * 8)) & 0xFF;
            hex[idx * 2 + 16] = KeyOf.HEX_CHARS[value >>> 4];
            hex[idx * 2 + 17] = KeyOf.HEX_CHARS[value & 0x0F];
        }
        return new String(hex);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other
            || other instanceof Key
            && this.hash().equals(Key.class.cast(other).hash());
    }

    @Override
    public int hashCode() {
        final long[] hash = this.hsh.value();
        long mixed = hash[0] ^ hash[1];
        mixed ^= mixed >>> 33;
        mixed *= 0xff51afd7ed558ccdL;
        return (int) mixed;
    }
}
