package com.hoioy.diamond.common.util;

import java.util.Set;

public interface ICommonCache {

    String get(String key);

    void set(String key, String value);

    void set(String key, String value, long expire);

    long increment(String key, long delta);

    void remove(String key);

    void mremove(Set<String> keys);

    void removeByPattern(String patternKey);
}






