package com.hoioy.diamond.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheType;

import java.util.Set;

//TODO 目前还不完善，推荐直接使用Redis缓存
public class CommonCacheUtil implements ICommonCache {

    private static final Logger logger = LoggerFactory.getLogger(CommonCacheUtil.class);

    public static String getCacheKey(String dtoName, String dtoId) {
        return dtoName + ":" + dtoId;
    }

    @Value("${spring.cache.type}")
    private CacheType cacheType;

    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Autowired
    private CommonCaffeineUtil commonCaffeineUtil;

    private ICommonCache getCurrentCacheUtil() {
        if (cacheType.equals(CacheType.REDIS)) {
            return commonRedisUtil;
        } else {
            return commonCaffeineUtil;
        }
    }

    public String get(String key) {
        return getCurrentCacheUtil().get(key);
    }

    public void set(String key, String value) {
        getCurrentCacheUtil().set(key, value);
    }

    public void set(String key, String value, long expire) {
        getCurrentCacheUtil().set(key, value, expire);
    }

    public long increment(String key, long delta) {
        return getCurrentCacheUtil().increment(key, delta);
    }

    public void remove(String key) {
        getCurrentCacheUtil().remove(key);
    }

    public void mremove(Set<String> keys) {
        getCurrentCacheUtil().mremove(keys);
    }

    public void removeByPattern(String patternKey) {
        getCurrentCacheUtil().removeByPattern(patternKey);
    }
}






