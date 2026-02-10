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
import com.github.fabriciofx.cactoos.cache.metadata.MetadataOf;
import com.github.fabriciofx.cactoos.cache.policies.ImmediatePolicies;
import com.github.fabriciofx.cactoos.cache.policy.ExpiredPolicy;
import java.time.LocalDateTime;
import org.cactoos.scalar.Unchecked;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * {@link Policed} tests.
 *
 * @since 0.0.7
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class PolicedTest {
    @Test
    void expiredPolicy() {
        final Cache<Word, Synonyms> cache = new Policed<>(
            new CacheOf<>(),
            new ImmediatePolicies<>(
                new ExpiredPolicy<>()
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new Synonyms("x", "y", "z"),
                new MetadataOf()
                    .with("expiration", LocalDateTime.now().minusSeconds(1L))
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new Synonyms("k", "l", "m"),
                new MetadataOf()
                    .with("expiration", LocalDateTime.now().plusSeconds(1L))
            )
        );
        new Assertion<>(
            "must check whether the eviction is the expired one",
            new Unchecked<>(() -> cache.evicted().get(0).key()),
            new HasValue<>(new KeyOf<>(new Word("a")))
        ).affirm();
    }
}
