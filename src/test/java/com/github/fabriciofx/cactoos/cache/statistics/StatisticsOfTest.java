/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.statistics;

import org.cactoos.scalar.ScalarOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Mismatches;
import org.llorllale.cactoos.matchers.Throws;

/**
 * StatisticsOf tests.
 * @since 0.0.10
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class StatisticsOfTest {
    @Test
    void resetAnEmpty() {
        new Assertion<>(
            "must reset an empty statistics",
            new Throws<>(Exception.class),
            new Mismatches<>(
                new ScalarOf<>(
                    () -> {
                        new StatisticsOf().reset();
                        return true;
                    }
                ),
                """
                Exception has type 'java.lang.Exception' and message matches \
                ANYTHING\
                """,
                "The exception wasn't thrown."
            )
        ).affirm();
    }
}
