/*
 * SPDX-FileCopyrightText: Copyright (C) 2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.cactoos.cache.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe Map that preserves insertion order.
 *
 * <p>This implementation uses a {@link LinkedHashMap} for insertion-order
 * iteration and a {@link ReentrantLock} for thread safety.</p>
 *
 * @param <K> the key type
 * @param <V> the value type
 * @since 0.0.13
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ConcurrentLinkedMap<K, V> implements Map<K, V> {
    /**
     * Lock.
     */
    private final Lock lock;

    /**
     * Backing map.
     */
    private final Map<K, V> map;

    /**
     * Ctor.
     */
    public ConcurrentLinkedMap() {
        this(new LinkedHashMap<>());
    }

    /**
     * Ctor.
     * @param origin The backing map
     */
    public ConcurrentLinkedMap(final Map<K, V> origin) {
        this.lock = new ReentrantLock();
        this.map = origin;
    }

    @Override
    public int size() {
        this.lock.lock();
        try {
            return this.map.size();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        this.lock.lock();
        try {
            return this.map.isEmpty();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean containsKey(final Object key) {
        this.lock.lock();
        try {
            return this.map.containsKey(key);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean containsValue(final Object value) {
        this.lock.lock();
        try {
            return this.map.containsValue(value);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public V get(final Object key) {
        this.lock.lock();
        try {
            return this.map.get(key);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public V put(final K key, final V value) {
        this.lock.lock();
        try {
            return this.map.put(key, value);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public V remove(final Object key) {
        this.lock.lock();
        try {
            return this.map.remove(key);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> src) {
        this.lock.lock();
        try {
            this.map.putAll(src);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void clear() {
        this.lock.lock();
        try {
            this.map.clear();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        this.lock.lock();
        try {
            return Collections.unmodifiableSet(
                new LinkedHashSet<>(this.map.keySet())
            );
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Collection<V> values() {
        this.lock.lock();
        try {
            return Collections.unmodifiableList(
                new ArrayList<>(this.map.values())
            );
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        this.lock.lock();
        try {
            return Collections.unmodifiableSet(
                new LinkedHashSet<>(this.map.entrySet())
            );
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public V getOrDefault(final Object key, final V def) {
        this.lock.lock();
        try {
            return this.map.getOrDefault(key, def);
        } finally {
            this.lock.unlock();
        }
    }
}
