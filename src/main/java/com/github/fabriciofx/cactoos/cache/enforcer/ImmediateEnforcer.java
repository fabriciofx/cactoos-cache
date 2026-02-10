/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.enforcer;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Enforcer;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Policy;
import java.util.List;
import org.cactoos.Bytes;

/**
 * Immediate enforcer of policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
public final class ImmediateEnforcer<K extends Bytes, V extends Bytes>
    implements Enforcer<K, V> {
    @Override
    public List<Entry<K, V>> apply(
        final Cache<K, V> cache,
        final List<Policy<K, V>> policies
    ) {
        for (final Policy<K, V> policy : policies) {
            policy.apply(cache);
        }
        return cache.evicted();
    }
}
