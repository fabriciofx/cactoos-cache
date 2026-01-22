/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import com.github.fabriciofx.cactoos.cache.base.CacheOf;
import com.github.fabriciofx.cactoos.cache.entry.EntryOf;
import com.github.fabriciofx.cactoos.cache.key.KeyOf;
import java.util.List;
import org.cactoos.bytes.BytesOf;
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
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class CacheTest {
    @Test
    void saveAndRetrieve() throws Exception {
        final Cache<String, List<String>> cache = new CacheOf<>();
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
        new Assertion<>(
            "must save and retrieve a cache entry",
            new HasValues<>(
                cache.store().retrieve(
                    new KeyOf<>("a", new BytesOf("a"))
                ).value()
            ),
            new Matches<>(new ListOf<>("x", "y", "z"))
        ).affirm();
    }

    @Test
    void saveAndDelete() throws Exception {
        final Cache<String, List<String>> cache = new CacheOf<>();
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
        new Assertion<>(
            "must save and delete a cache entry",
            new HasValues<>(
                cache.store().delete(
                    new KeyOf<>("a", new BytesOf("a"))
                ).value()
            ),
            new Matches<>(new ListOf<>("x", "y", "z"))
        ).affirm();
    }
}
