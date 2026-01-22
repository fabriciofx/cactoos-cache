/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.keys;

import com.github.fabriciofx.cactoos.cache.Key;
import com.github.fabriciofx.cactoos.cache.Keys;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged Keys.
 * @param <K> the key value type
 * @since 0.0.1
 * @checkstyle ParameterNumberCheck (300 lines)
 */
public final class Logged<K> implements Keys<K> {
    /**
     * Keys.
     */
    private final Keys<K> origin;

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
     * @param keys The cache to be logged
     * @param from Where the data comes from
     */
    public Logged(final Keys<K> keys, final String from) {
        this(keys, from, Logger.getLogger(from));
    }

    /**
     * Ctor.
     *
     * @param keys The cache to be logged
     * @param from Where the data comes from
     * @param logger The logger
     */
    public Logged(
        final Keys<K> keys,
        final String from,
        final Logger logger
    ) {
        this(
            keys,
            from,
            logger,
            new Unchecked<>(
                new Sticky<>(
                    () -> {
                        Level lvl = logger.getLevel();
                        if (lvl == null) {
                            Logger parent = logger;
                            while (lvl == null) {
                                parent = parent.getParent();
                                lvl = parent.getLevel();
                            }
                        }
                        return lvl;
                    }
                )
            )
        );
    }

    /**
     * Ctor.
     *
     * @param keys The cache to be logged
     * @param from Where the data comes from
     * @param logger The logger
     * @param level The logger level
     */
    public Logged(
        final Keys<K> keys,
        final String from,
        final Logger logger,
        final Unchecked<Level> level
    ) {
        this.origin = keys;
        this.from = from;
        this.logger = logger;
        this.level = level;
    }

    @Override
    public int count() {
        final int size = this.origin.count();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Retrieve the amount of cache keys: %d",
                    this.from,
                    size
                )
            ).asString()
        );
        return size;
    }

    @Override
    public void clear() {
        this.origin.clear();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Cleaning the cache keys",
                    this.from
                )
            ).asString()
        );
    }

    @Override
    public Iterator<Key<K>> iterator() {
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Retrieve the cache keys",
                    this.from
                )
            ).asString()
        );
        return this.origin.iterator();
    }
}
