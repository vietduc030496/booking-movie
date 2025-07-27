package com.ntu.common.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheUtil {

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(50)
                .build();
    }

    private static final Cache<String, Object> cache;

    private CaffeineCacheUtil() {}

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.getIfPresent(key);
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = cache.getIfPresent(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }

    public static boolean containsKey(String key) {
        return cache.getIfPresent(key) != null;
    }

    public static void invalidate(String key) {
        cache.invalidate(key);
    }

    public static void clearAll() {
        cache.invalidateAll();
    }
}
