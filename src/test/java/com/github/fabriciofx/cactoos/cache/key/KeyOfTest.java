/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.key;

import com.github.fabriciofx.cactoos.cache.Word;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * KeyOf tests.
 * @since 0.0.10
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class KeyOfTest {
    @Test
    void hashAsValue() {
        new Assertion<>(
            "Must have the same hash value",
            new KeyOf<>(new Word("test")).hash().value(),
            new IsEqual<>(0xbc13f8e0f3048bedL)
        ).affirm();
    }

    @Test
    void hashAsString() {
        new Assertion<>(
            "Must have the same hash string",
            new TextOf(new KeyOf<>(new Word("test")).hash().asString()),
            new IsText("bc13f8e0f3048bed")
        ).affirm();
    }

    @Test
    void hashAsInt() {
        new Assertion<>(
            "Must have the same hash integer",
            new KeyOf<>(new Word("test")).hash().asInt(),
            new IsEqual<>(1_326_936_845)
        ).affirm();
    }
}
