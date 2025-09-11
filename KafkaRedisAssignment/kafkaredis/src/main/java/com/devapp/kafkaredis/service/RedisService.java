package com.devapp.kafkaredis.service;

import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.request.UserDataRequest;
import jakarta.validation.Valid;

public interface RedisService {
    void save(UserDataRequest request);

    UserData findById(String userId);
}
