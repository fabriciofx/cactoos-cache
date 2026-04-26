/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.metadata;

import com.github.fabriciofx.cactoos.cache.Metadata;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.cactoos.list.ListOf;
import org.cactoos.set.SetOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Matches;

/**
 * MetadataOf tests.
 * @since 0.0.7
 */
@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")
final class MetadataOfTest {

    @Test
    void retrieveNames() {
        new Assertion<>(
            "must retrieve metadata names",
            new HasValues<>(
                new MetadataOf()
                    .with("name", "John")
                    .with("age", 30).names()
            ),
            new Matches<>(new SetOf<>("name", "age"))
        ).affirm();
    }

    @Test
    void createANewStringValue() {
        new Assertion<>(
            "must create a new metadata with a string value",
            () -> new MetadataOf()
                .with("color", "red")
                .value("color", new TypeOf<String>() { })
                .get(0),
            new IsText("red")
        ).affirm();
    }

    @Test
    void createANewSetOfStrings() {
        final Set<String> tables = new SetOf<>("a", "b", "c");
        new Assertion<>(
            "must create a new metadata with a set of strings value",
            new HasValues<>(
                new MetadataOf()
                    .with("tables", tables)
                    .value("tables", new TypeOf<Set<String>>() { }).get(0)
            ),
            new Matches<>(tables)
        ).affirm();
    }

    @Test
    void createANewExpiration() {
        final Metadata meta = new MetadataOf()
            .with("expiration", 5)
            .with("unit", TimeUnit.SECONDS);
        new Assertion<>(
            "must create a new metadata with an expiration value",
            new IsNumber(
                meta.value(
                    "expiration",
                    new TypeOf<Integer>() { }
                ).get(0)
            ),
            new Matches<>(5)
        ).affirm();
        new Assertion<>(
            "must create a new metadata with a unit value",
            () -> meta.value("unit", new TypeOf<TimeUnit>() { }).get(0),
            new HasValue<>(TimeUnit.SECONDS)
        ).affirm();
    }

    @Test
    void checkIfHasAnyAValue() {
        new Assertion<>(
            "must check if metadata contains any value",
            new MetadataOf()
                .with("name", "John")
                .with("tables", new SetOf<>("x", "y", "z"))
                .hasAny(new ListOf<>("John")),
            new IsTrue()
        ).affirm();
    }

    @Test
    void checkIfHasAnyAValueInSet() {
        new Assertion<>(
            "must check if metadata contains any value",
            new MetadataOf()
                .with("name", "John")
                .with("tables", new SetOf<>("x", "y", "z"))
                .hasAny(new ListOf<>("y")),
            new IsTrue()
        ).affirm();
    }
}
