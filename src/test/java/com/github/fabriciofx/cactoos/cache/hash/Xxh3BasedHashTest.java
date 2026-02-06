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
 * Xxh3BasedHash tests.
 * @since 0.0.10
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class Xxh3BasedHashTest {
    @Test
    void checkHash() {
        new Assertion<>(
            "must calculate the correct XXH3 based hash",
            new Xxh3BasedHash(
                new BytesOf("The quick brown fox jumps over the lazy dog")
            ).value(),
            new IsEqual<>(7_143_729_657_231_461_092L)
        ).affirm();
    }

    @Test
    void checkHex() {
        new Assertion<>(
            "must calculate the correct XXH3 based hexadecimal hash",
            () -> new Xxh3BasedHash(
                new BytesOf("The quick brown fox jumps over the lazy dog")
            ).asString(),
            new IsText("6323a0462eb44ae4")
        ).affirm();
    }
}
