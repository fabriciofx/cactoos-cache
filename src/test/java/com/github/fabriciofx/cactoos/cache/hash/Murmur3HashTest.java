/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.hash;

import org.cactoos.bytes.BytesOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Murmur3Hash tests.
 * @since 0.0.10
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class Murmur3HashTest {
    @Test
    void checkHash() {
        new Assertion<>(
            "must calculate the correct MURMUR3 hash",
            new Murmur3Hash(
                new BytesOf("The quick brown fox jumps over the lazy dog")
            ).value(),
            new IsEqual<>(
                new long[] {
                    -2_068_352_364_225_029_268L,
                    8_809_951_995_912_426_311L,
                }
            )
        ).affirm();
    }

    @Test
    void checkHex() {
        new Assertion<>(
            "must calculate the correct MURMUR3 hexadecimal hash",
            () -> new Murmur3Hash(
                new BytesOf("The quick brown fox jumps over the lazy dog")
            ).asString(),
            new IsText("6c1b07bc7bbc4be347939ac4a93c437a")
        ).affirm();
    }
}
