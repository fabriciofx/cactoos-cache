/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Word;
import com.github.fabriciofx.cactoos.cache.enforcer.ImmediateEnforcer;
import com.github.fabriciofx.cactoos.cache.entry.EntryOf;
import com.github.fabriciofx.cactoos.cache.key.KeyOf;
import com.github.fabriciofx.cactoos.cache.policy.MaxSizePolicy;
import java.util.List;
import org.cactoos.list.ListOf;
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
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>(new Word("a")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the hits statistic",
            () -> stats.statistic("hits").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkLookups() {
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>(new Word("a")));
        cache.store().contains(new KeyOf<>(new Word("a")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the lookups statistic",
            () -> stats.statistic("lookups").value(),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void checkInvalidations() {
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().delete(new KeyOf<>(new Word("a")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the invalidations statistic",
            () -> stats.statistic("invalidations").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkMisses() {
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>(new Word("b")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the misses statistic",
            () -> stats.statistic("misses").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkEvictions() {
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new Policed<>(
                new CacheOf<>(),
                new ImmediateEnforcer<>(),
                new MaxSizePolicy<>(1)
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new ListOf<>("k", "l", "m")
            )
        );
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the evictions statistic",
            () -> stats.statistic("evictions").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkInsertions() {
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new ListOf<>("k", "l", "m")
            )
        );
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the insertions statistic",
            () -> stats.statistic("insertions").value(),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void checkReplacements() {
        final Cache<Word, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("i", "j", "k")
            )
        );
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the replacements statistic",
            () -> stats.statistic("replacements").value(),
            new HasValue<>(1)
        ).affirm();
    }
}
