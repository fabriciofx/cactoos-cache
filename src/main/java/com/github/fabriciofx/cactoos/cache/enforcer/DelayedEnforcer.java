/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.enforcer;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Enforcer;
import com.github.fabriciofx.cactoos.cache.Policy;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Delayed enforcer of policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.7
 */
public final class DelayedEnforcer<K extends Bytes, V extends Bytes>
    implements Enforcer<K, V>, AutoCloseable {
    /**
     * Execute executor only once.
     */
    private final List<Unchecked<ScheduledExecutorService>> once;

    /**
     * Delay between executions.
     */
    private final long delay;

    /**
     * Time unit.
     */
    private final TimeUnit unit;

    /**
     * Executor.
     */
    private final Unchecked<ScheduledExecutorService> executor;

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
        this(
            delay,
            unit,
            new Unchecked<>(
                new Sticky<>(
                    Executors::newSingleThreadScheduledExecutor
                )
            )
        );
    }

    /**
     * Ctor.
     *
     * @param delay Delay between executions
     * @param unit Time unit
     * @param executor The executor to run the policies
     */
    public DelayedEnforcer(
        final long delay,
        final TimeUnit unit,
        final Unchecked<ScheduledExecutorService> executor
    ) {
        this.once = new ListOf<>();
        this.delay = delay;
        this.unit = unit;
        this.executor = executor;
    }

    @Override
    public void apply(
        final Cache<K, V> cache,
        final List<Policy<K, V>> policies
    ) {
        if (this.once.isEmpty()) {
            this.executor.value().scheduleWithFixedDelay(
                () -> {
                    for (final Policy<K, V> policy : policies) {
                        policy.apply(cache);
                    }
                },
                0L,
                this.delay,
                this.unit
            );
            this.once.add(this.executor);
        }
    }

    @Override
    public void close() {
        this.executor.value().shutdown();
    }
}
