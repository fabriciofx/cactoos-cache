/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Synonyms;
import com.github.fabriciofx.cactoos.cache.Word;
import com.github.fabriciofx.cactoos.cache.entry.EntryOf;
import com.github.fabriciofx.cactoos.cache.key.KeyOf;
import com.github.fabriciofx.fake.logger.FakeLogger;
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
    void logSaveAndRetrieve() {
        final FakeLogger logger = new FakeLogger();
        final Cache<Word, Synonyms> cache = new Logged<>(
            new CacheOf<>(),
            "cache",
            logger
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>(new Word("a")));
        new Assertion<>(
            "must log save and retrieve from cache",
            new HasString(
                """
                [cache] Storing in cache with key \
                'c654297dc7d34acc' and value '[x, y, z]'
                [cache] Retrieving from cache with key \
                'c654297dc7d34acc' and value '[x, y, z]'
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
    void logSaveAndDelete() {
        final FakeLogger logger = new FakeLogger();
        final Cache<Word, Synonyms> cache = new Logged<>(
            new CacheOf<>(),
            "cache",
            logger
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new Synonyms("k", "l", "m")
            )
        );
        cache.store().delete(new KeyOf<>(new Word("b")));
        new Assertion<>(
            "must log save and delete from cache",
            new HasString(
                """
                [cache] Storing in cache with key \
                '906a98983c4ba12d' and value '[k, l, m]'
                [cache] Deleting into cache with key \
                '906a98983c4ba12d' and returning \
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
