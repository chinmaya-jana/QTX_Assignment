package com.devapp.kafkaredis.service.impl;

import com.devapp.kafkaredis.mapper.UserDataMapper;
import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.repo.RedisRepository;
import com.devapp.kafkaredis.request.UserDataRequest;
import com.devapp.kafkaredis.service.RedisService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisRepository redisRepository;
    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Override
    public void save(UserDataRequest request) {
        UserData user = UserDataMapper.toEntity(request);
        redisRepository.save(user);
    }

    @Override
    public UserData findById(String userId) {
        return redisRepository.findById(userId);
    }
}
