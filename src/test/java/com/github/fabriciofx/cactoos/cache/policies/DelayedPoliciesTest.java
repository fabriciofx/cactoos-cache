/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.policies;

import com.github.fabriciofx.cactoos.cache.Cache;
import com.github.fabriciofx.cactoos.cache.Policies;
import com.github.fabriciofx.cactoos.cache.Policy;
import com.github.fabriciofx.cactoos.cache.Synonyms;
import com.github.fabriciofx.cactoos.cache.Word;
import com.github.fabriciofx.cactoos.cache.base.CacheOf;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * {@link DelayedPolicies} tests.
 *
 * @since 0.0.15
 */
final class DelayedPoliciesTest {

    @Test
    @Timeout(5)
    void dontBlockCallerWhenApplying() {
        try (
            Policies<Word, Synonyms> policies = new DelayedPolicies<>(
                43L,
                TimeUnit.MILLISECONDS,
                new ListOf<Policy<Word, Synonyms>>(cache -> cache.size())
            )
        ) {
            new Assertion<>(
                "must return to the caller after scheduling delayed policies",
                () -> {
                    policies.apply(new CacheOf<>());
                    return true;
                },
                new HasValue<>(true)
            ).affirm();
        }
    }

    @Test
    @Timeout(5)
    void appliesPoliciesRepeatedly() throws Exception {
        final CountDownLatch latch = new CountDownLatch(3);
        try (
            Policies<Word, Synonyms> policies = new DelayedPolicies<>(
                17L,
                TimeUnit.MILLISECONDS,
                new ListOf<Policy<Word, Synonyms>>(cache -> latch.countDown())
            )
        ) {
            policies.apply(new CacheOf<>());
            new Assertion<>(
                "must apply delayed policies repeatedly in background",
                latch.await(4L, TimeUnit.SECONDS),
                new IsTrue()
            ).affirm();
        }
    }

    @Test
    @Timeout(5)
    void throwsWhenPolicyFails() {
        final Cache<Word, Synonyms> cache = new CacheOf<>();
        try (
            Policies<Word, Synonyms> policies = new DelayedPolicies<>(
                29L,
                TimeUnit.MILLISECONDS,
                new ListOf<Policy<Word, Synonyms>>(
                    any -> {
                        throw new UnsupportedOperationException("broken");
                    }
                )
            )
        ) {
            new Assertion<>(
                "must fail fast when a delayed policy fails",
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        policies.apply(cache);
                    }
                    return cache;
                },
                new Throws<>(IllegalStateException.class)
            ).affirm();
        }
    }
}
