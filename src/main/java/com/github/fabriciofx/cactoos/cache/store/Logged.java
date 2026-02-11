/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.store;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import com.github.fabriciofx.cactoos.cache.Store;
import com.github.fabriciofx.cactoos.cache.log.LogLevel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.Bytes;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Ternary;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged Store.
 * <p>A {@link Store} decorator to logging store operations.
 * @param <K> the key value type
 * @param <V> the entry value type
 * @since 0.0.1
 * @checkstyle ParameterNumberCheck (500 lines)
 */
public final class Logged<K extends Bytes, V extends Bytes>
    implements Store<K, V> {
    /**
     * Store.
     */
    private final Store<K, V> origin;

    /**
     * Where the logs come from.
     */
    private final String from;

    /**
     * Logger.
     */
    private final Logger logger;

    /**
     * Level log.
     */
    private final Unchecked<Level> level;

    /**
     * Ctor.
     *
     * @param store The cache to be logged
     * @param from Where the data comes from
     */
    public Logged(final Store<K, V> store, final String from) {
        this(store, from, Logger.getLogger(from));
    }

    /**
     * Ctor.
     *
     * @param store The cache to be logged
     * @param from Where the data comes from
     * @param logger The logger
     */
    public Logged(
        final Store<K, V> store,
        final String from,
        final Logger logger
    ) {
        this(
            store,
            from,
            logger,
            new Unchecked<>(
                new Sticky<>(
                    () -> new LogLevel(logger).value()
                )
            )
        );
    }

    /**
     * Ctor.
     *
     * @param store The cache to be logged
     * @param from Where the data comes from
     * @param logger The logger
     * @param level The logger level
     */
    public Logged(
        final Store<K, V> store,
        final String from,
        final Logger logger,
        final Unchecked<Level> level
    ) {
        this.origin = store;
        this.from = from;
        this.logger = logger;
        this.level = level;
    }

    @Override
    public Entry<K, V> retrieve(final Key<K> key) {
        final Entry<K, V> entry = this.origin.retrieve(key);
        final String text;
        if  (entry.valid()) {
            text = entry.value().toString();
        } else {
            text = "invalid";
        }
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Retrieving from cache with key '%s' and value '%s'",
                    this.from,
                    key.hash(),
                    text
                )
            ).asString()
        );
        return entry;
    }

    @Override
    public Entry<K, V> save(final Key<K> key, final Entry<K, V> entry) {
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Storing in cache with key '%s' and value '%s'",
                    this.from,
                    key.hash(),
                    entry.value().toString()
                )
            ).asString()
        );
        return this.origin.save(key, entry);
    }

    @Override
    public Entry<K, V> delete(final Key<K> key) {
        final Entry<K, V> entry = this.origin.delete(key);
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Deleting into cache with key '%s' and returning value '%s'",
                    this.from,
                    key.hash(),
                    new Unchecked<>(
                        new Ternary<>(
                            entry.valid(),
                            () -> entry.value().toString(),
                            () -> "(invalid)"
                        )
                    ).value()
                )
            ).asString()
        );
        return entry;
    }

    @Override
    public boolean contains(final Key<K> key) {
        final boolean exists = this.origin.contains(key);
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Checking if cache has a value for key '%s': %s",
                    this.from,
                    key.hash(),
                    exists
                )
            ).asString()
        );
        return exists;
    }

    @Override
    public Keys<K> keys() {
        return new com.github.fabriciofx.cactoos.cache.keys.Logged<>(
            this.origin.keys(),
            this.from,
            this.logger,
            this.level
        );
    }

    @Override
    public Entries<K, V> entries() {
        return new com.github.fabriciofx.cactoos.cache.entries.Logged<>(
            this.origin.entries(),
            this.from,
            this.logger,
            this.level
        );
    }
}
