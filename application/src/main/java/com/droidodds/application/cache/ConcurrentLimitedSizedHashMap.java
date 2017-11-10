package com.droidodds.application.cache;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Laszlo_Sisa
 */
public class ConcurrentLimitedSizedHashMap<K, V> extends ConcurrentHashMap<K, V> {

    private final int sizeLimit;
    private final Queue<K> keyQueue;
    private final int evictChuckSize;

    public ConcurrentLimitedSizedHashMap(int sizeLimit) {
        super(sizeLimit);
        this.sizeLimit = sizeLimit;
        keyQueue = new LinkedList<>();
        evictChuckSize = ((Double) (sizeLimit * 0.2)).intValue();
    }

    @Override
    public V put(final K key, final V value) {
        evictIfNeeded();
        putInKeyQueue(key);
        return super.put(key, value);
    }

    @Override
    public V putIfAbsent(final K key, final V value) {
        evictIfNeeded();
        if (!contains(key)) {
            putInKeyQueue(key);
        }
        return super.putIfAbsent(key, value);
    }

    private void evictIfNeeded() {
        synchronized (this) {
            if (this.size() >= sizeLimit) {
                for (int i = 0; i < evictChuckSize && keyQueue.size() > 0; i++) {
                    remove(keyQueue.poll());
                }
            }
        }
    }

    private void putInKeyQueue(final K key) {
        synchronized (this) {
            keyQueue.add(key);
        }
    }
}
