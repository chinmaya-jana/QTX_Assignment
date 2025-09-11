package com.devapp.kafkaredis.mapper;

import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.request.UserDataRequest;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {
    public static UserData toEntity(UserDataRequest request) {
        if(request == null) return null;
        return new UserData(request.getId(), request.getName(), request.getEmail());
    }
}
