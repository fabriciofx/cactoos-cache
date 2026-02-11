/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.map;

import java.util.List;
import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.experimental.Threads;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * {@link ConcurrentLinkedMap} tests.
 *
 * @since 0.0.13
 * @checkstyle MagicNumberCheck (300 lines)
 * @checkstyle JavadocMethodCheck (300 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (300 lines)
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
            new IsEqual<>(List.of(10, 20, 30))
        ).affirm();
    }

    @Test
    void threadSafePuts() {
        final int threads = 10;
        final int per = 100;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        final List<Scalar<Boolean>> tasks = new ListOf<>();
        for (int idx = 0; idx < threads; ++idx) {
            final int offset = idx * per;
            tasks.add(
                () -> {
                    for (int num = 0; num < per; ++num) {
                        target.put(offset + num, offset + num);
                    }
                    return true;
                }
            );
        }
        new ListOf<>(new Threads<>(threads, tasks));
        new Assertion<>(
            "must contain all entries after concurrent puts",
            target.size(),
            new IsEqual<>(threads * per)
        ).affirm();
    }

    @Test
    void threadSafeReads() {
        final int total = 500;
        final int threads = 10;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        for (int idx = 0; idx < total; ++idx) {
            target.put(idx, idx);
        }
        final List<Scalar<Boolean>> tasks = new ListOf<>();
        for (int idx = 0; idx < threads; ++idx) {
            tasks.add(
                () -> {
                    boolean found = true;
                    for (int num = 0; num < total; ++num) {
                        if (!target.containsKey(num)) {
                            found = false;
                        }
                    }
                    return found;
                }
            );
        }
        new Assertion<>(
            "all concurrent reads must find every key",
            new ListOf<>(new Threads<>(threads, tasks))
                .stream().allMatch(val -> val),
            new IsTrue()
        ).affirm();
    }

    @Test
    void threadSafePutsAndRemoves() {
        final int total = 500;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        new ListOf<>(
            new Threads<>(
                2,
                () -> {
                    for (int idx = 0; idx < total; ++idx) {
                        target.put(idx, idx);
                    }
                    return true;
                },
                () -> {
                    for (int idx = 0; idx < total; ++idx) {
                        target.remove(idx);
                    }
                    return true;
                }
            )
        );
        new Assertion<>(
            "must not throw after concurrent puts and removes",
            target.size() <= total,
            new IsTrue()
        ).affirm();
    }

    @Test
    void noExceptionOnConcurrentIteration() {
        final int total = 200;
        final int threads = 5;
        final Map<Integer, Integer> target = new ConcurrentLinkedMap<>();
        for (int idx = 0; idx < total; ++idx) {
            target.put(idx, idx);
        }
        final List<Scalar<Boolean>> tasks = new ListOf<>();
        for (int idx = 0; idx < threads; ++idx) {
            final int id = idx;
            if (id % 2 == 0) {
                tasks.add(
                    () -> {
                        boolean valid = true;
                        for (final Integer key : target.keySet()) {
                            if (key < 0) {
                                valid = false;
                            }
                        }
                        return valid;
                    }
                );
            } else {
                tasks.add(
                    () -> {
                        target.put(total + id, id);
                        return true;
                    }
                );
            }
        }
        new Assertion<>(
            "must not throw ConcurrentModificationException",
            new ListOf<>(new Threads<>(threads, tasks))
                .stream().allMatch(val -> val),
            new IsTrue()
        ).affirm();
    }
}
