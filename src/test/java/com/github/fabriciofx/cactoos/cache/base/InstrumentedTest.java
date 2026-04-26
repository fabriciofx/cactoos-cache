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
import com.github.fabriciofx.cactoos.cache.policies.ImmediatePolicies;
import com.github.fabriciofx.cactoos.cache.policy.MaxCountPolicy;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Instrumented tests.
 * @since 0.0.1
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class InstrumentedTest {

    @Test
    void checkHits() {
        final Cache<Word, Synonyms> cache = new Instrumented<>(
            new CacheOf<>()
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
            "must check the hits statistic",
            () -> cache.statistics().statistic("hits").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkLookups() {
        final Cache<Word, Synonyms> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>(new Word("a")));
        cache.store().contains(new KeyOf<>(new Word("a")));
        new Assertion<>(
            "must check the lookups statistic",
            () -> cache.statistics().statistic("lookups").value(),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void checkInvalidations() {
        final Cache<Word, Synonyms> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().delete(new KeyOf<>(new Word("a")));
        new Assertion<>(
            "must check the invalidations statistic",
            () -> cache.statistics().statistic("invalidations").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkMisses() {
        final Cache<Word, Synonyms> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>(new Word("b")));
        new Assertion<>(
            "must check the misses statistic",
            () -> cache.statistics().statistic("misses").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkEvictions() {
        final Cache<Word, Synonyms> cache = new Policed<>(
            new Instrumented<>(
                new CacheOf<>()
            ),
            new ImmediatePolicies<>(
                new MaxCountPolicy<>(1)
            )
        );
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
            "must check the evictions statistic",
            () -> cache.statistics().statistic("evictions").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkInsertions() {
        final Cache<Word, Synonyms> cache = new Instrumented<>(
            new CacheOf<>()
        );
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
            "must check the insertions statistic",
            () -> cache.statistics().statistic("insertions").value(),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void checkReplacements() {
        final Cache<Word, Synonyms> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("i", "j", "k")
            )
        );
        new Assertion<>(
            "must check the replacements statistic",
            () -> cache.statistics().statistic("replacements").value(),
            new HasValue<>(1)
        ).affirm();
    }
}
