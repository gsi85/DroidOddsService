package com.droidodds.application.cache;

import java.util.concurrent.Callable;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * @author Laszlo_Sisa
 */
public class Cache2kAdapter implements Cache {

    private final org.cache2k.Cache cache;

    public Cache2kAdapter(final org.cache2k.Cache cache) {
        this.cache = cache;
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return cache;
    }

    @Override
    public ValueWrapper get(final Object key) {
        Object storedValue = cache.get(key);
        return storedValue != null ? new SimpleValueWrapper(storedValue): null;
    }

    @Override
    public <T> T get(final Object key, final Class<T> type) {
        return (T) cache.get(key);
    }

    @Override
    public <T> T get(final Object key, final Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        cache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(final Object key, final Object value) {
        cache.putIfAbsent(key, value);
        return new SimpleValueWrapper(value);
    }

    @Override
    public void evict(final Object key) {

    }

    @Override
    public void clear() {
        cache.clear();
    }

}
