package com.devapp.redisOP.service;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

public interface RedisService {
    Map<String, String> set(String key, String value);

    Map<String, String> get(String key);

    Map<String, String> delete(String key);

    Map<String, List<String>> lpush(String key, String value);

    Map<String, List<String>> rpush(String key, String value);

    Map<String, List<String>> getList(String key);

    Boolean exists(String key);
}
