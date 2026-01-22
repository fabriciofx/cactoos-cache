/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import com.github.fabriciofx.cactoos.cache.base.CacheOf;
import com.github.fabriciofx.cactoos.cache.base.Instrumented;
import com.github.fabriciofx.cactoos.cache.entry.EntryOf;
import com.github.fabriciofx.cactoos.cache.key.KeyOf;
import com.github.fabriciofx.cactoos.cache.policy.MaxSizePolicy;
import com.github.fabriciofx.cactoos.cache.store.StoreOf;
import java.util.List;
import org.cactoos.bytes.BytesOf;
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
    void checkHits() throws Exception {
        final Cache<String, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>("a", new BytesOf("a")),
            new EntryOf<>(
                new KeyOf<>("a", new BytesOf("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>("a", new BytesOf("a")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the hits statistic",
            () -> stats.statistic("hits").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkLookups() throws Exception {
        final Cache<String, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>("a", new BytesOf("a")),
            new EntryOf<>(
                new KeyOf<>("a", new BytesOf("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>("a", new BytesOf("a")));
        cache.store().contains(new KeyOf<>("a", new BytesOf("a")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the lookups statistic",
            () -> stats.statistic("lookups").value(),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void checkInvalidations() throws Exception {
        final Cache<String, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>("a", new BytesOf("a")),
            new EntryOf<>(
                new KeyOf<>("a", new BytesOf("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().delete(new KeyOf<>("a", new BytesOf("a")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the invalidations statistic",
            () -> stats.statistic("invalidations").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkMisses() throws Exception {
        final Cache<String, List<String>> cache = new Instrumented<>(
            new CacheOf<>()
        );
        cache.store().save(
            new KeyOf<>("a", new BytesOf("a")),
            new EntryOf<>(
                new KeyOf<>("a", new BytesOf("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().retrieve(new KeyOf<>("b", new BytesOf("b")));
        final Statistics stats = cache.statistics();
        new Assertion<>(
            "must check the misses statistic",
            () -> stats.statistic("misses").value(),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void checkEvictions() throws Exception {
        final Cache<String, List<String>> cache = new Instrumented<>(
            new CacheOf<>(
                new StoreOf<>(
                    new MaxSizePolicy<>(1)
                )
            )
        );
        cache.store().save(
            new KeyOf<>("a", new BytesOf("a")),
            new EntryOf<>(
                new KeyOf<>("a", new BytesOf("a")),
                new ListOf<>("x", "y", "z")
            )
        );
        cache.store().save(
            new KeyOf<>("b", new BytesOf("b")),
            new EntryOf<>(
                new KeyOf<>("b", new BytesOf("b")),
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
}
