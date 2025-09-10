package com.devapp.redisOP.service.impl;

import com.devapp.redisOP.exception.RedisOperationException;
import com.devapp.redisOP.repository.RedisRepository;
import com.devapp.redisOP.service.RedisService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);
    private final RedisRepository redisRepository;

    @Override
    public Map<String, String> set(String key, String value) {
        try {
            redisRepository.set(key, value);
            return redisRepository.get(key);
        }
        catch (Exception ex) {
            log.error("Failed to set key '{}' in Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to set value in Redis: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, String> get(String key) {
        try {
            return redisRepository.get(key);
        }
        catch (Exception ex) {
            log.error("Failed to fetch key '{}' from Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to fetch the key in Redis: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, String> delete(String key) {
        try {
            return redisRepository.delete(key);
        }
        catch (Exception ex) {
            log.error("Failed to delete key '{}' from Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to delete the key in Redis: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, List<String>> lpush(String key, String value) {
        try {
            redisRepository.lpush(key, value);
            return redisRepository.lrange(key, 0, -1);
        }
        catch (Exception ex) {
            log.error("Failed to LPUSH into key '{}' in Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to set value in Redis: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, List<String>> rpush(String key, String value) {
        try {
            redisRepository.rpush(key, value);
            return redisRepository.lrange(key, 0, -1);
        }
        catch (Exception ex) {
            log.error("Failed to RPUSH into key '{}' in Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to set value in Redis: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, List<String>> getList(String key) {
        try {
            return redisRepository.lrange(key, 0, -1);
        }
        catch (Exception ex) {
            log.error("Failed to fetch list for key '{}' from Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to fetch the key in Redis: " + ex.getMessage());
        }
    }

    @Override
    public Boolean exists(String key) {
        try {
            return redisRepository.exists(key);
        }
        catch (Exception ex) {
            log.error("Failed to check existence of key '{}' in Redis. Error: {}", key, ex.getMessage(), ex);
            throw new RedisOperationException("Failed to fetch the key in Redis: " + ex.getMessage());
        }
    }
}
