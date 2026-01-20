/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.words;

import com.github.fabriciofx.cactoos.cache.Statistics;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.base.CacheEnvelope;
import java.util.List;

/**
 * WordsCache.
 * @since 0.0.1
 */
public final class WordsCache extends CacheEnvelope<String, List<String>> {
    /**
     * Ctor.
     */
    public WordsCache() {
        super(new WordsStore());
    }

    /**
     * Ctor.
     * @param store A store
     */
    public WordsCache(final Store<String, List<String>> store) {
        super(store);
    }

    /**
     * Ctor.
     * @param store A store
     * @param statistics The statistics
     */
    public WordsCache(
        final Store<String, List<String>> store,
        final Statistics statistics
    ) {
        super(store, statistics);
    }
}
