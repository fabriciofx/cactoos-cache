/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.hash;

import com.github.fabriciofx.cactoos.cache.Hash;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.bytes.UncheckedBytes;
import org.cactoos.list.ListOf;

/**
 * Murmur3Hash.
 *
 * @since 0.0.1
 * @checkstyle CyclomaticComplexityCheck (200 lines)
 * @checkstyle JavaNCSSCheck (200 lines)
 * @checkstyle ExecutableStatementCountCheck (200 lines)
 * @checkstyle FallThroughCheck (200 lines)
 * @checkstyle UnnecessaryParenthesesCheck (200 lines)
 * @checkstyle BooleanExpressionComplexityCheck (200 lines)
 */
@SuppressWarnings(
    {
        "PMD.UnnecessaryCast",
        "PMD.ImplicitSwitchFallThrough",
        "PMD.NcssCount"
    }
)
public final class Murmur3Hash implements Hash<long[]> {
    /**
     * Hex chars.
     */
    private static final char[] HEX_CHARS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    /**
     * Cached hash.
     */
    private final List<long[]> cached;

    /**
     * Bytes.
     */
    private final Bytes bytes;

    /**
     * Seed.
     */
    private final int seed;

    /**
     * Ctor.
     *
     * @param bytes Bytes
     */
    public Murmur3Hash(final Bytes bytes) {
        this(bytes, 0);
    }

    /**
     * Ctor.
     *
     * @param bytes Bytes
     * @param seed The seed
     */
    public Murmur3Hash(final Bytes bytes, final int seed) {
        this.cached = new ListOf<>();
        this.bytes = bytes;
        this.seed = seed;
    }

    @SuppressWarnings("fallthrough")
    @Override
    public long[] value() {
        if (this.cached.isEmpty()) {
            final byte[] data = new UncheckedBytes(this.bytes).asBytes();
            final int length = data.length;
            final int blocks = length >> 4;
            final long first = 0x87c37b91114253d5L;
            final long second = 0x4cf5ad432745937fL;
            final long[] hash = new long[2];
            hash[0] = this.seed & 0xffffffffL;
            hash[1] = this.seed & 0xffffffffL;
            for (int num = 0; num < blocks; ++num) {
                final int index = num << 4;
                long keia = littleEndian(data, index);
                long keib = littleEndian(data, index + 8);
                keia *= first;
                keia = Long.rotateLeft(keia, 31);
                keia *= second;
                hash[0] ^= keia;
                hash[0] = Long.rotateLeft(hash[0], 27);
                hash[0] += hash[1];
                hash[0] = hash[0] * 5 + 0x52dce729;
                keib *= second;
                keib = Long.rotateLeft(keib, 33);
                keib *= first;
                hash[1] ^= keib;
                hash[1] = Long.rotateLeft(hash[1], 31);
                hash[1] += hash[0];
                hash[1] = hash[1] * 5 + 0x38495ab5;
            }
            long one = 0;
            long two = 0;
            final int tail = blocks << 4;
            switch (length & 15) {
                case 15:
                    two ^= ((long) data[tail + 14] & 0xff) << 48;
                case 14:
                    two ^= ((long) data[tail + 13] & 0xff) << 40;
                case 13:
                    two ^= ((long) data[tail + 12] & 0xff) << 32;
                case 12:
                    two ^= ((long) data[tail + 11] & 0xff) << 24;
                case 11:
                    two ^= ((long) data[tail + 10] & 0xff) << 16;
                case 10:
                    two ^= ((long) data[tail + 9] & 0xff) << 8;
                case 9:
                    two ^= ((long) data[tail + 8] & 0xff);
                    two *= second;
                    two = Long.rotateLeft(two, 33);
                    two *= first;
                    hash[1] ^= two;
                case 8:
                    one ^= ((long) data[tail + 7] & 0xff) << 56;
                case 7:
                    one ^= ((long) data[tail + 6] & 0xff) << 48;
                case 6:
                    one ^= ((long) data[tail + 5] & 0xff) << 40;
                case 5:
                    one ^= ((long) data[tail + 4] & 0xff) << 32;
                case 4:
                    one ^= ((long) data[tail + 3] & 0xff) << 24;
                case 3:
                    one ^= ((long) data[tail + 2] & 0xff) << 16;
                case 2:
                    one ^= ((long) data[tail + 1] & 0xff) << 8;
                case 1:
                    one ^= ((long) data[tail] & 0xff);
                    one *= first;
                    one = Long.rotateLeft(one, 31);
                    one *= second;
                    hash[0] ^= one;
                    break;
                default:
                    break;
            }
            hash[0] ^= length;
            hash[1] ^= length;
            hash[0] += hash[1];
            hash[1] += hash[0];
            hash[0] = fmix(hash[0]);
            hash[1] = fmix(hash[1]);
            hash[0] += hash[1];
            hash[1] += hash[0];
            this.cached.add(hash);
        }
        return this.cached.get(0);
    }

    @Override
    public String asString() {
        final long[] hash = this.value();
        final char[] hex = new char[32];
        for (int idx = 0; idx < 8; ++idx) {
            final int value = (int) (hash[0] >>> (idx * 8)) & 0xFF;
            hex[idx * 2] = Murmur3Hash.HEX_CHARS[value >>> 4];
            hex[idx * 2 + 1] = Murmur3Hash.HEX_CHARS[value & 0x0F];
        }
        for (int idx = 0; idx < 8; ++idx) {
            final int value = (int) (hash[1] >>> (idx * 8)) & 0xFF;
            hex[idx * 2 + 16] = Murmur3Hash.HEX_CHARS[value >>> 4];
            hex[idx * 2 + 17] = Murmur3Hash.HEX_CHARS[value & 0x0F];
        }
        return new String(hex);
    }

    @Override
    public int asInt() {
        final long[] hash = this.value();
        long mixed = hash[0] ^ hash[1];
        mixed ^= mixed >>> 33;
        mixed *= 0xff51afd7ed558ccdL;
        return (int) mixed;
    }

    private static long littleEndian(final byte[] bytes, final int offset) {
        return ((long) bytes[offset] & 0xff)
            | (((long) bytes[offset + 1] & 0xff) << 8)
            | (((long) bytes[offset + 2] & 0xff) << 16)
            | (((long) bytes[offset + 3] & 0xff) << 24)
            | (((long) bytes[offset + 4] & 0xff) << 32)
            | (((long) bytes[offset + 5] & 0xff) << 40)
            | (((long) bytes[offset + 6] & 0xff) << 48)
            | (((long) bytes[offset + 7] & 0xff) << 56);
    }

    private static long fmix(final long value) {
        long result = value;
        result ^= result >>> 33;
        result *= 0xff51afd7ed558ccdL;
        result ^= result >>> 33;
        result *= 0xc4ceb9fe1a85ec53L;
        result ^= result >>> 33;
        return result;
    }
}
