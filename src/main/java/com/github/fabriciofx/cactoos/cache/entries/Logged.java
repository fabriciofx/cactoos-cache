/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.entries;

import com.github.fabriciofx.cactoos.cache.Entries;
import com.github.fabriciofx.cactoos.cache.Entry;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.Text;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Logged Entries.
 * @param <D> the key domain type
 * @param <V> the entry value type
 * @since 0.0.1
 * @checkstyle ParameterNumberCheck (300 lines)
 */
public final class Logged<D, V> implements Entries<D, V> {
    /**
     * Entries.
     */
    private final Entries<D, V> origin;

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
     * @param entries The cache to be logged
     * @param from Where the data comes from
     */
    public Logged(final Entries<D, V> entries, final String from) {
        this(entries, from, Logger.getLogger(from));
    }

    /**
     * Ctor.
     *
     * @param entries The cache to be logged
     * @param from Where the data comes from
     * @param logger The logger
     */
    public Logged(
        final Entries<D, V> entries,
        final String from,
        final Logger logger
    ) {
        this(
            entries,
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
     * @param entries The cache to be logged
     * @param from Where the data comes from
     * @param logger The logger
     * @param level The logger level
     */
    public Logged(
        final Entries<D, V> entries,
        final String from,
        final Logger logger,
        final Unchecked<Level> level
    ) {
        this.origin = entries;
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
                    "[%s] Retrieve the amount of cache entries: %d",
                    this.from,
                    size
                )
            ).asString()
        );
        return size;
    }

    @Override
    public List<Entry<D, V>> invalidate(final Iterable<String> metadata) {
        final List<Entry<D, V>> invalidated = this.origin.invalidate(metadata);
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Invalidating %d cache entries with keys: '%s'",
                    this.from,
                    invalidated.size(),
                    new Joined(
                        new TextOf(", "),
                        new Mapped<Text>(
                            entry -> new TextOf(entry.key().hash()),
                            invalidated
                        )
                    )
                )
            ).asString()
        );
        return invalidated;
    }

    @Override
    public void clear() {
        this.origin.clear();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Cleaning the cache entries",
                    this.from
                )
            ).asString()
        );
    }

    @Override
    public Iterator<Entry<D, V>> iterator() {
        final Iterator<Entry<D, V>> entries = this.origin.iterator();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "[%s] Retrieve the cache entries",
                    this.from
                )
            ).asString()
        );
        return entries;
    }
}
