/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.log;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.Scalar;

/**
 * LogLevel.
 *
 * @since 0.0.13
 */
public final class LogLevel implements Scalar<Level> {
    /**
     * Logger.
     */
    private final Logger logger;

    /**
     * Default level.
     */
    private final Level def;

    /**
     * Ctor.
     * @param logger Logger
     */
    public LogLevel(final Logger logger) {
        this(logger, Level.INFO);
    }

    /**
     * Ctor.
     * @param logger Logger
     * @param def Default level
     */
    public LogLevel(final Logger logger, final Level def) {
        this.logger = logger;
        this.def = def;
    }

    @Override
    public Level value() throws Exception {
        Level level = this.def;
        for (Logger lgr = this.logger; lgr != null; lgr = lgr.getParent()) {
            final Level lvl = lgr.getLevel();
            if (lvl != null) {
                level = lvl;
                break;
            }
        }
        return level;
    }
}
