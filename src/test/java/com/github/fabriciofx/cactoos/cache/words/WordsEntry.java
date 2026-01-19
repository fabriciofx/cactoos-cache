/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.words;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.entry.EntryEnvelope;
import java.util.List;
import java.util.Map;

/**
 * WordsEntry.
 * @since 0.0.1
 */
public final class WordsEntry extends EntryEnvelope<String, List<String>> {
    /**
     * Ctor.
     * @param key A key
     * @param value A value
     */
    public WordsEntry(final Key<String> key, final List<String> value) {
        super(key, value);
    }

    /**
     * Ctor.
     * @param key A key
     * @param value A value
     * @param metadata Metadata
     */
    public WordsEntry(
        final Key<String> key,
        final List<String> value,
        final Map<String, List<String>> metadata
    ) {
        super(key, value, metadata);
    }
}
