package com.devapp.kafkaredis.repo.impl;

import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.repo.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(UserData user) {
        redisTemplate.opsForValue().set(user.getId(), user);
    }

    @Override
    public UserData findById(String userId) {
        Object obj = redisTemplate.opsForValue().get(userId);
        return obj != null ? (UserData) obj : null;
    }
}
