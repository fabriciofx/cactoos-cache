/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policies;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Policies;
import com.github.fabriciofx.cactoos.cache.Policy;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Delayed policies.
 *
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.13
 * @checkstyle ParameterNumberCheck (200 lines)
 */
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public final class DelayedPolicies<K extends Bytes, V extends Bytes>
    implements Policies<K, V> {
    /**
     * Items.
     */
    private final List<Policy<K, V>> items;

    /**
     * Execute executor only once.
     */
    private final AtomicBoolean once;

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
     * @param items Items
     */
    @SafeVarargs
    public DelayedPolicies(
        final long delay,
        final TimeUnit unit,
        final Policy<K, V>... items
    ) {
        this(
            delay,
            unit,
            new Unchecked<>(
                new Sticky<>(
                    Executors::newSingleThreadScheduledExecutor
                )
            ),
            new ListOf<>(items)
        );
    }

    /**
     * Ctor.
     *
     * @param delay Delay between executions
     * @param unit Time unit
     * @param items Items
     */
    public DelayedPolicies(
        final long delay,
        final TimeUnit unit,
        final List<Policy<K, V>> items
    ) {
        this(
            delay,
            unit,
            new Unchecked<>(
                new Sticky<>(
                    Executors::newSingleThreadScheduledExecutor
                )
            ),
            items
        );
    }

    /**
     * Ctor.
     *
     * @param delay Delay between executions
     * @param unit Time unit
     * @param executor The executor to run the policies
     * @param items Items
     */
    public DelayedPolicies(
        final long delay,
        final TimeUnit unit,
        final Unchecked<ScheduledExecutorService> executor,
        final List<Policy<K, V>> items
    ) {
        this.once = new AtomicBoolean(false);
        this.delay = delay;
        this.unit = unit;
        this.executor = executor;
        this.items = items;
    }

    @Override
    public void apply(final Cache<K, V> cache) {
        if (this.once.compareAndSet(false, true)) {
            this.executor.value().scheduleWithFixedDelay(
                () -> this.items.forEach(policy -> policy.apply(cache)),
                0L,
                this.delay,
                this.unit
            );
        }
    }

    @Override
    public void close() {
        this.executor.value().shutdownNow();
        try {
            this.executor.value().awaitTermination(5, TimeUnit.SECONDS);
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
