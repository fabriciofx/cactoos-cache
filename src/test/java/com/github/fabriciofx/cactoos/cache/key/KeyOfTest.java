/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.key;

import com.github.fabriciofx.cactoos.cache.Word;
import org.cactoos.text.TextOf;
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
    void hash() {
        new Assertion<>(
            "Must have the same hash",
            new TextOf(new KeyOf<>(new Word("test")).hash()),
            new IsText("bc13f8e0f3048bed")
        ).affirm();
    }
}
