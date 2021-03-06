package com.hoioy.diamond.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommonRedisUtil implements ICommonCache {

    private static final Logger logger = LoggerFactory.getLogger(CommonRedisUtil.class);

    @Autowired
    public StringRedisTemplate template;

    public void expire(String key, long expire) {
        this.template.expire(key, expire, TimeUnit.SECONDS);
        logger.debug("Set expire key={}, expire={}", key, expire);
    }

    public void expireAt(String key, Date date) {
        this.template.expireAt(key, date);
        logger.debug("Set expireAt key={}, date={}", key, date);
    }

    public String get(String key) {
        logger.debug("Enter get() key={}", key);
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.get(key);
    }

    public void set(String key, String value) {
        logger.debug("Enter set() key={}, value={}", key, value);
        ValueOperations<String, String> ops = this.template.opsForValue();
        ops.set(key, value);
    }

    /**
     * 设置同时设置过期时间
     *
     * @param key    键
     * @param value  值
     * @param expire 过期时间，单位：秒
     */
    public void set(String key, String value, long expire) {
        logger.debug("Enter set() key={}, value={}", key, value);
        ValueOperations<String, String> ops = this.template.opsForValue();
        ops.set(key, value, expire, TimeUnit.SECONDS);
    }

    public long increment(String key, long delta) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.increment(key, delta);
    }

    public List<String> mget(Set<String> keys) {
        logger.debug("Enter mget() keys={}", keys);
        return template.opsForValue().multiGet(keys);
    }

    public void mset(Map<String, String> map) {
        logger.debug("Enter mset() keys={}, value={}", map.keySet(), map.values());
        template.opsForValue().multiSet(map);
    }

    public void mremove(Set<String> keys) {
        logger.debug("Enter mremove() keys={}", keys);
        template.delete(keys);
    }

    public void remove(String key) {
        logger.debug("Enter remove() key={}", key);
        this.template.delete(key);
    }

    public void removeByPattern(String patternKey) {
        logger.debug("Enter removeByPattern() key={}", patternKey);
        Set<String> keys = template.keys(patternKey);
        this.template.delete(keys);
    }

    public String getHash(String key, String hashKey) {
        logger.debug("Enter getHash() key={},hashKey={}", key, hashKey);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.get(key, hashKey);
    }


    public void setHash(String key, String hashKey, String value) {
        logger.debug("Enter setHash() key={},hashKey={}, value={}", key, hashKey, value);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.put(key, hashKey, value);
    }

    public void removeHash(String key, String hashKey) {
        logger.debug("Enter removeHash() key={} hashKey={}", key, hashKey);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.delete(key, hashKey);
    }

    public List<String> getHashValues(String key) {
        logger.debug("Enter getHashValues() key={}", key);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.values(key);
    }

    public Map getHashEntries(String key) {
        logger.debug("Enter getHashEntries() key={}", key);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.entries(key);
    }

    public Boolean incrementHashLong(String hashname, String itemkey, Long delta) {
        logger.debug("Enter hashIncrementLong() hashname={},itemkey={},delta={}", hashname, itemkey, delta);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();

        String countStr = hashOps.get(hashname, itemkey);
        if (countStr == null) {
            hashOps.put(hashname, itemkey, delta + "");
        } else {
            //增加统计量
            hashOps.increment(hashname, itemkey, delta);
        }
        return true;
    }

    public List<String> mgetHash(String key, Set<String> keys) {
        logger.debug("Enter getHash() key={},hashKey.size={}", key, keys.size());
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.multiGet(key, keys);
    }

    public void msetHash(String key, Map<String, String> map) {
        logger.debug("Enter getHash() key={},hash.size={}", key, map.size());
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.putAll(key, map);
    }

    public void mremoveHash(String key, String[] hashKeys) {
        logger.debug("Enter mremoveHash() key={} hashKeys={}", key, hashKeys);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.delete(key, hashKeys);
    }

    /**
     * 注意，此方法时间复杂度为O(n)，慎用
     *
     * @param pattern pattern支持glob-style的通配符格式，如*表示任意一个或多个字符，?表示任意字符，[abc]表示方括号中任意一个字母
     * @return
     */
    public List<String> mgetByPattern(String pattern) {
        logger.debug("Enter mgetByPattern() pattern={}", pattern);
        Set<String> keys = getKeysSetByPattern(pattern);
        return mget(keys);
    }

    /**
     * 注意，此方法时间复杂度为O(n)，慎用
     *
     * @param pattern pattern支持glob-style的通配符格式，如*表示任意一个或多个字符，?表示任意字符，[abc]表示方括号中任意一个字母
     */
    public void mremoveByPattern(String pattern) {
        logger.debug("Enter mgetByPattern() pattern={}", pattern);
        Set<String> keys = getKeysSetByPattern(pattern);
        mremove(keys);
    }

    public Set<String> getKeysSetByPattern(String pattern) {
        logger.debug("Enter getKeysByPattern() pattern={}", pattern);
        return template.keys(pattern);
    }
}






