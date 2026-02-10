/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policy;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.metadata.TypeOf;
import java.time.LocalDateTime;
import java.util.List;
import org.cactoos.Bytes;

/**
 * ExpiredPolicy.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
public final class ExpiredPolicy<K extends Bytes, V extends Bytes>
    implements Policy<K, V> {
    @Override
    public void apply(final Cache<K, V> cache) {
        final List<Entry<K, V>> evicted = cache.evicted();
        final Store<K, V> store = cache.store();
        final LocalDateTime now = LocalDateTime.now();
        for (final Key<K> key : store.keys()) {
            final Entry<K, V> entry = store.retrieve(key);
            final List<LocalDateTime> expiration = entry.metadata()
                .value("expiration", new TypeOf<>() { });
            if (!expiration.isEmpty() && expiration.get(0).isBefore(now)) {
                evicted.add(store.delete(key));
            }
        }
    }
}
