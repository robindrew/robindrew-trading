package com.robindrew.trading.util.collect;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A map optimised for frequent reads and rare writes.
 * <p>
 * Reads run lock-free against an immutable snapshot; every mutation replaces that snapshot under a
 * lock.
 */
public class CopyOnWriteMap<K, V> implements Map<K, V> {

    private final Object lock = new Object();

    private volatile Map<K, V> map = Map.of();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        synchronized (lock) {
            Map<K, V> copy = new LinkedHashMap<>(map);
            V previous = copy.put(key, value);
            map = ImmutableMap.copyOf(copy);
            return previous;
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        synchronized (lock) {
            V previous = map.get(key);
            if (previous != null) {
                return previous;
            }
            Map<K, V> copy = new LinkedHashMap<>(map);
            copy.put(key, value);
            map = ImmutableMap.copyOf(copy);
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (lock) {
            if (!map.containsKey(key)) {
                return null;
            }
            Map<K, V> copy = new LinkedHashMap<>(map);
            V previous = copy.remove(key);
            map = ImmutableMap.copyOf(copy);
            return previous;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> values) {
        synchronized (lock) {
            Map<K, V> copy = new LinkedHashMap<>(map);
            copy.putAll(values);
            map = ImmutableMap.copyOf(copy);
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            map = Map.of();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (function == null) {
            throw new NullPointerException("function");
        }
        synchronized (lock) {
            Map<K, V> copy = new LinkedHashMap<>(map);
            for (Entry<K, V> entry : copy.entrySet()) {
                entry.setValue(function.apply(entry.getKey(), entry.getValue()));
            }
            map = ImmutableMap.copyOf(copy);
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object object) {
        return map.equals(object);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
