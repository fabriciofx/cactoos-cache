/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.words;

import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.store.StoreEnvelope;
import java.util.List;

/**
 * WordsStore.
 * @since 0.0.1
 */
public final class WordsStore extends StoreEnvelope<String, List<String>> {
    /**
     * Ctor.
     */
    public WordsStore() {
        super();
    }

    /**
     * Ctor.
     * @param policy The policy
     */
    public WordsStore(final Policy<String, List<String>> policy) {
        super(policy);
    }
}
