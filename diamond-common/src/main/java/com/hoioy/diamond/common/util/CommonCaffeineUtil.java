package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.dto.CaffeineCacheDTO;
import com.hoioy.diamond.common.exception.CommonException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommonCaffeineUtil implements ICommonCache {

    private static final Logger logger = LoggerFactory.getLogger(CommonCaffeineUtil.class);

    private Cache<String, CaffeineCacheDTO> caffeineCache = Caffeine.newBuilder().recordStats()
            .expireAfter(new Expiry<String, CaffeineCacheDTO>() {
                @Override
                public long expireAfterCreate(String key, CaffeineCacheDTO dto, long currentTime) {
                    return TimeUnit.NANOSECONDS.convert(dto.getExpireSecond(), TimeUnit.SECONDS);
                }

                @Override
                public long expireAfterUpdate(String key, CaffeineCacheDTO dto, long currentTime, long currentDuration) {
                    return currentDuration;
                }

                @Override
                public long expireAfterRead(String key, CaffeineCacheDTO dto, long currentTime, long currentDuration) {
                    return currentDuration;
                }
            })
            .build();

    @Override
    public String get(String key) {
        logger.debug("Enter get() key={}", key);
        CaffeineCacheDTO dto = caffeineCache.getIfPresent(key);
        return dto == null ? null : dto.getValue();
    }

    @Override
    public void set(String key, String value) {
        logger.debug("Enter set() key={}, value={}", key, value);
        caffeineCache.put(key, new CaffeineCacheDTO(Long.MAX_VALUE, value));
    }

    @Override
    public void set(String key, String value, long expire) {
        logger.debug("Enter set() key={}, value={}", key, value);
        caffeineCache.put(key, new CaffeineCacheDTO(expire, value));
    }

    @Override
    public long increment(String key, long delta) {
        Long value = Long.valueOf(get(key)) + delta;
        set(key, String.valueOf(value));
        return value;
    }

    @Override
    public void remove(String key) {
        logger.debug("Enter remove() key={}", key);
        caffeineCache.invalidate(key);
    }

    @Override
    public void mremove(Set<String> keys) {
        logger.debug("Enter mremove() keys={}", keys);
        caffeineCache.invalidateAll(keys);
    }

    @Override
    public void removeByPattern(String patternKey) {
        logger.debug("Enter remove() key={}", patternKey);
        if (!patternKey.contains("*")) {
            throw new CommonException("Caffenion目前不支持此patter匹配");
        }
        Set<String> keySet = caffeineCache.asMap().keySet();
        Set<String> needRemoveKeys = new HashSet<>();
        for (String key : keySet) {
            if (key.contains(patternKey.substring(0, patternKey.length() - 1))) {
                needRemoveKeys.add(key);
            }
        }
        caffeineCache.invalidateAll(needRemoveKeys);
    }
}






