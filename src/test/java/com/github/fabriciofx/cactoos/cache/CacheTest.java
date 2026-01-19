/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import com.github.fabriciofx.cactoos.cache.words.WordsCache;
import com.github.fabriciofx.cactoos.cache.words.WordsEntry;
import com.github.fabriciofx.cactoos.cache.words.WordsKey;
import java.util.List;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Matches;

/**
 * CacheTest.
 * <p>Tests for {@link Cache}.
 * @since 0.0.1
 */
final class CacheTest {
    @Test
    void saveAndRetrieve() throws Exception {
        final Cache<String, List<String>> cache = new WordsCache();
        cache.store().save(
            new WordsKey("a"),
            new WordsEntry(
                new WordsKey("a"),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().save(
            new WordsKey("b"),
            new WordsEntry(
                new WordsKey("b"),
                new ListOf<>("k", "l", "m")
            )
        );
        new Assertion<>(
            "must save and retrieve a cache entry",
            new HasValues<>(cache.store().retrieve(new WordsKey("a")).value()),
            new Matches<>(new ListOf<>("x", "y", "z"))
        ).affirm();
    }

    @Test
    void saveAndDelete() throws Exception {
        final Cache<String, List<String>> cache = new WordsCache();
        cache.store().save(
            new WordsKey("a"),
            new WordsEntry(
                new WordsKey("a"),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().save(
            new WordsKey("b"),
            new WordsEntry(
                new WordsKey("b"),
                new ListOf<>("k", "l", "m")
            )
        );
        new Assertion<>(
            "must save and delete a cache entry",
            new HasValues<>(cache.store().delete(new WordsKey("a")).value()),
            new Matches<>(new ListOf<>("x", "y", "z"))
        ).affirm();
    }
}
