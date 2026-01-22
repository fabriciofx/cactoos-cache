/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import com.github.fabriciofx.cactoos.cache.base.Logged;
import com.github.fabriciofx.cactoos.cache.words.WordsCache;
import com.github.fabriciofx.cactoos.cache.words.WordsEntry;
import com.github.fabriciofx.cactoos.cache.words.WordsKey;
import com.github.fabriciofx.fake.logger.FakeLogger;
import java.util.List;
import org.cactoos.list.ListOf;
import org.cactoos.text.Replaced;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.Matches;

/**
 * Logged tests.
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class LoggedTest {
    @Test
    void logSaveAndRetrieve() throws Exception {
        final FakeLogger logger = new FakeLogger();
        final Cache<String, List<String>> cache = new Logged<>(
            new WordsCache(),
            "cache",
            logger
        );
        cache.store().save(
            new WordsKey("a"),
            new WordsEntry(
                new WordsKey("a"),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new WordsKey("a"));
        new Assertion<>(
            "must log save and retrieve from cache",
            new HasString(
                """
                [cache] Storing in cache with key \
                '897859f6655555855a890e51483ab5e6' and value '[x, y, z]'
                [cache] Retrieving from cache with key \
                '897859f6655555855a890e51483ab5e6' and value '[x, y, z]'
                """
            ),
            new Matches<>(
                new Replaced(
                    new TextOf(logger.toString()),
                    "\r\n",
                    "\n"
                )
            )
        ).affirm();
    }

    @Test
    void logSaveAndDelete() throws Exception {
        final FakeLogger logger = new FakeLogger();
        final Cache<String, List<String>> cache = new Logged<>(
            new WordsCache(),
            "cache",
            logger
        );
        cache.store().save(
            new WordsKey("b"),
            new WordsEntry(
                new WordsKey("b"),
                new ListOf<>("k", "l", "m")
            )
        );
        cache.store().delete(new WordsKey("b"));
        new Assertion<>(
            "must log save and delete from cache",
            new HasString(
                """
                [cache] Storing in cache with key \
                'eed1d3b157a9987ae9944e541e132efa' and value '[k, l, m]'
                [cache] Deleting into cache with key \
                'eed1d3b157a9987ae9944e541e132efa' and returning \
                value '[k, l, m]'
                """
            ),
            new Matches<>(
                new Replaced(
                    new TextOf(logger.toString()),
                    "\r\n",
                    "\n"
                )
            )
        ).affirm();
    }
}
