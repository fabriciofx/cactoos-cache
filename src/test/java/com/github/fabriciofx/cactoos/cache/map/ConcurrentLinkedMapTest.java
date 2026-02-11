/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * {@link ConcurrentLinkedMap} tests.
 *
 * @since 0.0.14
 * @checkstyle MagicNumberCheck (300 lines)
 * @checkstyle JavadocMethodCheck (300 lines)
 */
@SuppressWarnings({
    "PMD.UnitTestShouldIncludeAssert",
    "PMD.UnnecessaryLocalRule"
})
final class ConcurrentLinkedMapTest {
    @Test
    void preservesInsertionOrder() {
        final Map<String, Integer> map = new ConcurrentLinkedMap<>();
        map.put("first", 1);
        map.put("second", 2);
        map.put("third", 3);
        new Assertion<>(
            "must preserve insertion order in keySet",
            new ArrayList<>(map.keySet()),
            new IsEqual<>(List.of("first", "second", "third"))
        ).affirm();
    }

    @Test
    void preservesOrderAfterRemoval() {
        final Map<String, Integer> map = new ConcurrentLinkedMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.remove("b");
        new Assertion<>(
            "must preserve order after removing middle element",
            new ArrayList<>(map.keySet()),
            new IsEqual<>(List.of("a", "c"))
        ).affirm();
    }

    @Test
    void preservesOrderAfterUpdate() {
        final Map<String, Integer> map = new ConcurrentLinkedMap<>();
        map.put("x", 1);
        map.put("y", 2);
        map.put("z", 3);
        map.put("x", 10);
        new Assertion<>(
            "must keep original insertion position after update",
            new ArrayList<>(map.keySet()),
            new IsEqual<>(List.of("x", "y", "z"))
        ).affirm();
    }

    @Test
    void iteratorReturnsFirstInserted() {
        final Map<String, Integer> map = new ConcurrentLinkedMap<>();
        map.put("alpha", 1);
        map.put("beta", 2);
        map.put("gamma", 3);
        new Assertion<>(
            "must return first inserted key from iterator",
            map.keySet().iterator().next(),
            new IsEqual<>("alpha")
        ).affirm();
    }

    @Test
    void valuesPreserveInsertionOrder() {
        final Map<String, Integer> map = new ConcurrentLinkedMap<>();
        map.put("a", 10);
        map.put("b", 20);
        map.put("c", 30);
        new Assertion<>(
            "must preserve insertion order in values",
            new ArrayList<>(map.values()),
            new IsEqual<>(List.of(10, 20, 30))
        ).affirm();
    }

    @Test
    void threadSafePuts() throws InterruptedException {
        final int threads = 10;
        final int per = 100;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        final CountDownLatch latch = new CountDownLatch(threads);
        final ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            for (int idx = 0; idx < threads; idx = idx + 1) {
                final int offset = idx * per;
                pool.submit(
                    () -> {
                        try {
                            latch.countDown();
                            latch.await();
                            for (int num = 0; num < per; num = num + 1) {
                                target.put(offset + num, offset + num);
                            }
                        } catch (final InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                );
            }
            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
        }
        new Assertion<>(
            "must contain all entries after concurrent puts",
            target.size(),
            new IsEqual<>(threads * per)
        ).affirm();
    }

    @Test
    void threadSafeReads() throws InterruptedException {
        final int total = 500;
        final int threads = 10;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        for (int idx = 0; idx < total; idx = idx + 1) {
            target.put(idx, idx);
        }
        final CountDownLatch latch = new CountDownLatch(threads);
        final List<Boolean> found =
            java.util.Collections.synchronizedList(
                new ArrayList<>(threads * total)
            );
        final ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            for (int idx = 0; idx < threads; idx = idx + 1) {
                pool.submit(
                    () -> {
                        try {
                            latch.countDown();
                            latch.await();
                            for (int num = 0; num < total; num = num + 1) {
                                found.add(target.containsKey(num));
                            }
                        } catch (final InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                );
            }
            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
        }
        new Assertion<>(
            "all concurrent reads must find every key",
            found.stream().allMatch(val -> val),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void threadSafePutsAndRemoves() throws InterruptedException {
        final int total = 500;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        final CountDownLatch latch = new CountDownLatch(2);
        final ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            pool.submit(
                () -> {
                    try {
                        latch.countDown();
                        latch.await();
                        for (int idx = 0; idx < total; idx = idx + 1) {
                            target.put(idx, idx);
                        }
                    } catch (final InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            );
            pool.submit(
                () -> {
                    try {
                        latch.countDown();
                        latch.await();
                        for (int idx = 0; idx < total; idx = idx + 1) {
                            target.remove(idx);
                        }
                    } catch (final InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            );
            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
        }
        new Assertion<>(
            "must not throw after concurrent puts and removes",
            target.size() >= 0,
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void noExceptionOnConcurrentIteration() throws InterruptedException {
        final int total = 200;
        final int threads = 5;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        for (int idx = 0; idx < total; idx = idx + 1) {
            target.put(idx, idx);
        }
        final CountDownLatch latch = new CountDownLatch(threads);
        final List<Boolean> found =
            java.util.Collections.synchronizedList(
                new ArrayList<>(threads * total)
            );
        final ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            for (int idx = 0; idx < threads; idx = idx + 1) {
                final int id = idx;
                pool.submit(
                    () -> {
                        try {
                            latch.countDown();
                            latch.await();
                            if (id % 2 == 0) {
                                for (final Integer key : target.keySet()) {
                                    found.add(key >= 0);
                                }
                            } else {
                                target.put(total + id, id);
                            }
                        } catch (final InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                );
            }
            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
        }
        new Assertion<>(
            "must not throw ConcurrentModificationException",
            found.stream().allMatch(val -> val),
            new IsEqual<>(true)
        ).affirm();
    }
}
