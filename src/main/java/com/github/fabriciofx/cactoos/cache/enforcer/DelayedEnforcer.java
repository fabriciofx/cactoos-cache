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
import org.cactoos.scalar.Unchecked;

/**
 * Delayed enforcer of policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class DelayedEnforcer<K extends Bytes, V> implements
    Enforcer<K, V> {
    /**
     * Enforced.
     */
    private final List<Unchecked<List<Entry<K, V>>>> enforced;

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
        this.enforced = new ListOf<>();
        this.delay = delay;
        this.unit = unit;
    }

    @Override
    public List<Entry<K, V>> apply(
        final Cache<K, V> cache,
        final List<Policy<K, V>> policies
    ) {
        if (this.enforced.isEmpty()) {
            this.enforced.add(
                new Unchecked<>(
                    () -> {
                        final List<Entry<K, V>> evicted = cache.evicted();
                        final ScheduledExecutorService executor = Executors
                            .newSingleThreadScheduledExecutor();
                        return executor.schedule(
                            () -> {
                                for (final Policy<K, V> policy : policies) {
                                    evicted.addAll(policy.apply(cache.store()));
                                }
                                return evicted;
                            },
                            this.delay,
                            this.unit
                        ).get();
                    }
                )
            );
        }
        return this.enforced.get(0).value();
    }
}
