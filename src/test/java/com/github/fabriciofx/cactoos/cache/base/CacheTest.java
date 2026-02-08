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
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * CacheTest.
 * <p>Tests for {@link Cache}.
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class CacheTest {
    @Test
    void saveAndRetrieve() {
        final Cache<Word, Synonyms> cache = new CacheOf<>();
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new Synonyms("k", "l", "m")
            )
        );
        new Assertion<>(
            "must save and retrieve a cache entry",
            cache.store().retrieve(
                new KeyOf<>(new Word("a"))
            ).value(),
            new IsEqual<>(new Synonyms("x", "y", "z"))
        ).affirm();
    }

    @Test
    void saveAndDelete() {
        final Cache<Word, Synonyms> cache = new CacheOf<>();
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new Synonyms("k", "l", "m")
            )
        );
        new Assertion<>(
            "must save and delete a cache entry",
                cache.store().delete(
                    new KeyOf<>(new Word("a"))
                ).value(),
            new IsEqual<>(new Synonyms("x", "y", "z"))
        ).affirm();
    }

    @Test
    void size() {
        final Cache<Word, Synonyms> cache = new CacheOf<>();
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new Synonyms("k", "l", "m")
            )
        );
        new Assertion<>(
            "must check the cache size in bytes",
            cache.size(),
            new IsEqual<>(10)
        ).affirm();
    }
}
