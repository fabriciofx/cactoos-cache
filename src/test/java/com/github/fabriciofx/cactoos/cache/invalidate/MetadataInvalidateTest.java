/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.invalidate;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Word;
import com.github.fabriciofx.cactoos.cache.base.CacheOf;
import com.github.fabriciofx.cactoos.cache.entry.EntryOf;
import com.github.fabriciofx.cactoos.cache.key.KeyOf;
import com.github.fabriciofx.cactoos.cache.metadata.MetadataOf;
import java.util.List;
import org.cactoos.list.ListOf;
import org.cactoos.set.SetOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Invalidate tests.
 * @since 0.0.7
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class MetadataInvalidateTest {
    @Test
    void invalidateIfMetadataHasAny() throws Exception {
        final Cache<Word, List<String>> cache = new CacheOf<>();
        cache.store().save(
            new KeyOf<>(new Word("a")),
            new EntryOf<>(
                new KeyOf<>(new Word("a")),
                new ListOf<>("x", "y", "z"),
                new MetadataOf().with("tables", new SetOf<>("i", "j", "k"))
            )
        );
        cache.store().save(
            new KeyOf<>(new Word("b")),
            new EntryOf<>(
                new KeyOf<>(new Word("b")),
                new ListOf<>("k", "l", "m")
            )
        );
        final List<Entry<Word, List<String>>> invalidated = cache
            .store()
            .entries()
            .invalidate(new MetadataInvalidate<>(new ListOf<>("j")));
        new Assertion<>(
            "must invalidate a cache entry if metadata contains a value",
            () -> invalidated.get(0).key().value(),
            new HasValue<>(new Word("a"))
        ).affirm();
    }

}
