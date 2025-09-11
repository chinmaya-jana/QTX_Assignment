package com.devapp.kafkaredis.repo;

import com.devapp.kafkaredis.model.UserData;

public interface RedisRepository {
    void save(UserData user);

    UserData findById(String userId);
}
