/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.base;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Word;
import com.github.fabriciofx.cactoos.cache.enforcer.ImmediateEnforcer;
import com.github.fabriciofx.cactoos.cache.entry.EntryOf;
import com.github.fabriciofx.cactoos.cache.key.KeyOf;
import com.github.fabriciofx.cactoos.cache.metadata.MetadataOf;
import com.github.fabriciofx.cactoos.cache.policy.ExpiredPolicy;
import java.time.LocalDateTime;
import java.util.List;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

final class PolicedTest {
    @Test
    void expiredPolicy() throws Exception {
        final Cache<Word, List<String>> cache = new Policed<>(
            new CacheOf<>(),
            new ImmediateEnforcer<>(),
            new ExpiredPolicy<>()
        );
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z"),
                new MetadataOf()
                    .with("expiration", LocalDateTime.now().minusSeconds(1L))
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new ListOf<>("k", "l", "m"),
                new MetadataOf()
                    .with("expiration", LocalDateTime.now().plusSeconds(1L))
            )
        );
        new Assertion<>(
            "must check the evictions entries",
            cache.evicted(),
            new HasSize(1)
        ).affirm();
    }
}
