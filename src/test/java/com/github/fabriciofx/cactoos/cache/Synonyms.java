/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * Synonyms.
 * @since 0.0.12
 */
public final class Synonyms implements Bytes {
    /**
     * Words.
     */
    private final List<Word> words;

    /**
     * Ctor.
     * @param words A list of words
     */
    public Synonyms(final String... words) {
        this(new ListOf<>(words).stream().map(Word::new).toList());
    }

    /**
     * Ctor.
     * @param words A list of words
     */
    public Synonyms(final List<Word> words) {
        this.words = words;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (final Word word : this.words) {
            stream.write(word.asBytes());
        }
        return stream.toByteArray();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other
            || other instanceof Synonyms
            && this.words.equals(Synonyms.class.cast(other).words);
    }

    @Override
    public int hashCode() {
        return this.words.hashCode();
    }

    @Override
    public String toString() {
        return this.words.toString();
    }
}
