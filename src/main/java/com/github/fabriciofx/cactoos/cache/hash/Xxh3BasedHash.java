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
 * Xxh3BasedHash.
 *
 * @since 0.0.10
 * @checkstyle BooleanExpressionComplexityCheck (200 lines)
 */
@SuppressWarnings({"PMD.UnnecessaryLocalRule", "PMD.UnnecessaryCast"})
public final class Xxh3BasedHash implements Hash<Long> {
    /**
     * Prime constant.
     */
    private static final long PRIME1 = 0x9E3779B185EBCA87L;

    /**
     * Prime constant.
     */
    private static final long PRIME2 = 0xC2B2AE3D27D4EB4FL;

    /**
     * Prime constant.
     */
    private static final long PRIME3 = 0x165667B19E3779F9L;

    /**
     * Prime constant.
     */
    private static final long PRIME4 = 0x85EBCA77C2B2AE63L;

    /**
     * Prime constant.
     */
    private static final long PRIME5 = 0x165667919E3779F9L;

    /**
     * Secret.
     */
    private static final long[] SECRET = {
        0x9E3779B185EBCA87L,
        0xC2B2AE3D27D4EB4FL,
        0x165667B19E3779F9L,
        0x85EBCA77C2B2AE63L,
        0x27D4EB2F165667C5L,
        0x9FB21C651E98DF25L,
        0xA5A35625E0C3F21DL,
        0xC3F21D9FB21C651EL,
    };

    /**
     * Bytes.
     */
    private final Bytes bytes;

    /**
     * Hash only once.
     */
    private final List<Long> once;

    /**
     * Ctor.
     *
     * @param bytes Bytes
     */
    public Xxh3BasedHash(final Bytes bytes) {
        this.once = new ListOf<>();
        this.bytes = bytes;
    }

    @Override
    public Long value() {
        if (this.once.isEmpty()) {
            final byte[] data = new UncheckedBytes(this.bytes).asBytes();
            final int len = data.length;
            long hash = len * Xxh3BasedHash.PRIME1;
            int idx = 0;
            while (idx + 32 <= len) {
                final long first = littleEndian(data, idx)
                    ^ Xxh3BasedHash.SECRET[0];
                final long second = littleEndian(data, idx + 8)
                    ^ Xxh3BasedHash.SECRET[1];
                final long third = littleEndian(data, idx + 16)
                    ^ Xxh3BasedHash.SECRET[2];
                final long fourth = littleEndian(data, idx + 24)
                    ^ Xxh3BasedHash.SECRET[3];
                hash += mulFold(first, Xxh3BasedHash.PRIME1);
                hash ^= mulFold(second, Xxh3BasedHash.PRIME2);
                hash += mulFold(third, Xxh3BasedHash.PRIME3);
                hash ^= mulFold(fourth, Xxh3BasedHash.PRIME4);
                hash = Long.rotateLeft(hash, 27) * Xxh3BasedHash.PRIME1
                    + Xxh3BasedHash.PRIME4;
                idx += 32;
            }
            while (idx + 8 <= len) {
                final long value = littleEndian(data, idx)
                    ^ Xxh3BasedHash.SECRET[(idx >> 3) & 7];
                hash ^= mulFold(value, Xxh3BasedHash.PRIME2);
                hash = Long.rotateLeft(hash, 23) * Xxh3BasedHash.PRIME3
                    + Xxh3BasedHash.PRIME1;
                idx += 8;
            }
            long last = 0;
            for (int count = 0; idx < len; ++idx, count += 8) {
                last |= (long) (data[idx] & 0xFF) << count;
            }
            hash ^= mulFold(
                last ^ Xxh3BasedHash.SECRET[7],
                Xxh3BasedHash.PRIME4
            );
            this.once.add(avalanche(hash));
        }
        return this.once.get(0);
    }

    @Override
    public String asString() {
        return Long.toHexString(this.value());
    }

    @Override
    public int asInt() {
        return this.value().hashCode();
    }

    private static long mulFold(final long first, final long second) {
        final long high = Math.multiplyHigh(first, second);
        final long low = first * second;
        return high ^ low;
    }

    private static long avalanche(final long value) {
        long hash = value;
        hash ^= hash >>> 37;
        hash *= Xxh3BasedHash.PRIME5;
        hash ^= hash >>> 32;
        return hash;
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
}
