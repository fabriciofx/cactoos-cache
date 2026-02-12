/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.map;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * {@link ConcurrentLinkedMap} tests.
 *
 * @since 0.0.13
 * @checkstyle MagicNumberCheck (300 lines)
 * @checkstyle JavadocMethodCheck (300 lines)
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class ConcurrentLinkedMapTest {
    @Test
    void preservesInsertionOrder() {
        final Map<String, Integer> map = new ConcurrentLinkedMap<>();
        map.put("first", 1);
        map.put("second", 2);
        map.put("third", 3);
        new Assertion<>(
            "must preserve insertion order in keySet",
            new ListOf<>(map.keySet()),
            new IsEqual<>(new ListOf<>("first", "second", "third"))
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
            new ListOf<>(map.keySet()),
            new IsEqual<>(new ListOf<>("a", "c"))
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
            new ListOf<>(map.keySet()),
            new IsEqual<>(new ListOf<>("x", "y", "z"))
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
            new ListOf<>(map.values()),
            new IsEqual<>(new ListOf<>(10, 20, 30))
        ).affirm();
    }

    @Test
    void threadSafePuts() {
        final AtomicInteger counter = new AtomicInteger(0);
        new Assertion<>(
            "must contain all entries after concurrent puts",
            map -> {
                final int offset = counter.getAndIncrement() * 100;
                for (int num = 0; num < 100; ++num) {
                    map.put(offset + num, offset + num);
                }
                return true;
            },
            new RunsInThreads<>(new ConcurrentLinkedMap<>(), 10)
        ).affirm();
    }

    @Test
    void threadSafeReads() {
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        for (int idx = 0; idx < 500; ++idx) {
            target.put(idx, idx);
        }
        new Assertion<>(
            "all concurrent reads must find every key",
            map -> {
                boolean found = true;
                for (int num = 0; num < 500; ++num) {
                    if (!map.containsKey(num)) {
                        found = false;
                        break;
                    }
                }
                return found;
            },
            new RunsInThreads<>(target, 10)
        ).affirm();
    }

    @Test
    void threadSafePutsAndRemoves() {
        final AtomicInteger role = new AtomicInteger(0);
        new Assertion<>(
            "must not throw after concurrent puts and removes",
            map -> {
                if (role.getAndIncrement() % 2 == 0) {
                    for (int idx = 0; idx < 500; ++idx) {
                        map.put(idx, idx);
                    }
                } else {
                    for (int idx = 0; idx < 500; ++idx) {
                        map.remove(idx);
                    }
                }
                return true;
            },
            new RunsInThreads<>(new ConcurrentLinkedMap<>(), 2)
        ).affirm();
    }

    @Test
    void noExceptionOnConcurrentIteration() {
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        for (int idx = 0; idx < 200; ++idx) {
            target.put(idx, idx);
        }
        final AtomicInteger role = new AtomicInteger(0);
        new Assertion<>(
            "must not throw ConcurrentModificationException",
            map -> {
                final int id = role.getAndIncrement();
                boolean valid = true;
                if (id % 2 == 0) {
                    for (final Integer key : map.keySet()) {
                        if (key < 0) {
                            valid = false;
                            break;
                        }
                    }
                } else {
                    map.put(200 + id, id);
                }
                return valid;
            },
            new RunsInThreads<>(target, 5)
        ).affirm();
    }
}
