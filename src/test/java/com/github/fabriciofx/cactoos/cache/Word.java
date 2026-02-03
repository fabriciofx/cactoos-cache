/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.nio.charset.StandardCharsets;
import org.cactoos.Bytes;

/**
 * Word.
 * @since 0.0.6
 */
public final class Word implements Bytes {
    /**
     * Content.
     */
    private final String content;

    /**
     * Ctor.
     * @param content The content
     */
    public Word(final String content) {
        this.content = content;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.content.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other
            || other instanceof Word
            && this.content.equals(Word.class.cast(other).content);
    }

    @Override
    public int hashCode() {
        return this.content.hashCode();
    }
}
