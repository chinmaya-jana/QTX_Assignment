package com.devapp.redisOP.repository.impl;

import com.devapp.redisOP.repository.RedisRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final ListOperations<String, String> listOps;

    public RedisRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOps = redisTemplate.opsForList();
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key.trim(), value.trim());
    }

    @Override
    public Map<String, String> get(String key) {
        String value = redisTemplate.opsForValue().get(key.trim());

        return value != null ? Map.of(key, value) : Map.of();
    }

    @Override
    public Map<String, String> delete(String key) {
        String value = redisTemplate.opsForValue().getAndDelete(key);
        return value != null ? Map.of(key, value) : Map.of();
    }

    @Override
    public void lpush(String key, String value) {
        listOps.leftPush(key, value);
    }

    @Override
    public Map<String, List<String>> lrange(String key, int start, int end) {
        List<String> values = listOps.range(key, start, end);
        return values != null ? Map.of(key, values) : Map.of();
    }

    @Override
    public void rpush(String key, String value) {
        listOps.rightPush(key, value);
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}
