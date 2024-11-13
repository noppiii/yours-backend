package com.example.tes.coding.yours.service.global.impl;

import com.example.tes.coding.yours.service.global.InMemoryCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InMemoryCacheServiceImpl implements InMemoryCacheService {

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    @Override
    public void setData(String key, Object value, Long time, TimeUnit timeUnit) {
        long expirationTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(time, timeUnit);
        cache.put(key, new CacheEntry(value, expirationTime));
    }

    @Override
    public Object getData(String key) {
        CacheEntry entry = cache.get(key);
        if (entry != null && (entry.getExpirationTime() == -1 || entry.getExpirationTime() > System.currentTimeMillis())) {
            return entry.getValue();
        }
        cache.remove(key);
        return null;
    }

    @Override
    public void deleteData(String key) {
        cache.remove(key);
    }

    @Override
    public void increaseData(String key) {
        CacheEntry entry = cache.get(key);
        if (entry != null && entry.getValue() instanceof Long) {
            entry.setValue((Long) entry.getValue() + 1);
        }
    }

    private static class CacheEntry {
        private Object value;
        private long expirationTime;

        public CacheEntry(Object value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}