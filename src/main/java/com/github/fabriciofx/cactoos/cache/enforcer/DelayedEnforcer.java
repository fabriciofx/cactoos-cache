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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;

/**
 * Delayed enforcer of policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
public final class DelayedEnforcer<K extends Bytes, V> implements
    Enforcer<K, V> {
    /**
     * Cached evicted list.
     */
    private final List<List<Entry<K, V>>> cached;

    /**
     * Delay between executions.
     */
    private final long delay;

    /**
     * Time unit.
     */
    private final TimeUnit unit;

    /**
     * Ctor.
     *
     * @param delay Delay between executions
     * @param unit Time unit
     */
    public DelayedEnforcer(
        final long delay,
        final TimeUnit unit
    ) {
        this.cached = new ListOf<>();
        this.delay = delay;
        this.unit = unit;
    }

    @Override
    public List<Entry<K, V>> apply(
        final Cache<K, V> cache,
        final List<Policy<K, V>> policies
    ) {
        if (this.cached.isEmpty()) {
            final List<Entry<K, V>> evicted = cache.evicted();
            final ScheduledExecutorService executor = Executors
                .newSingleThreadScheduledExecutor();
            executor.scheduleWithFixedDelay(
                () -> {
                    for (final Policy<K, V> policy : policies) {
                        evicted.addAll(policy.apply(cache.store()));
                    }
                },
                0L,
                this.delay,
                this.unit
            );
            this.cached.add(evicted);
        }
        return this.cached.get(0);
    }
}
