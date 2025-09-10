package com.devapp.redisOP.repository;

import java.util.List;
import java.util.Map;

public interface RedisRepository {
    void set(String key, String value);

    Map<String, String> get(String key);

    Map<String, String> delete(String key);

    void lpush(String key, String value);

    Map<String, List<String>> lrange(String key, int start, int end);

    void rpush(String key, String value);

    Boolean exists(String key);
}
