/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.words;

import com.github.fabriciofx.cactoos.cache.key.KeyEnvelope;
import org.cactoos.bytes.BytesOf;

/**
 * WordsKey.
 * @since 0.0.1
 */
public final class WordsKey extends KeyEnvelope<String> {
    /**
     * Ctor.
     * @param value A value
     */
    public WordsKey(final String value) {
        super(value, new BytesOf(value));
    }
}
