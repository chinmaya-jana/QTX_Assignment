package com.devapp.kafkaredis.service;

import com.devapp.kafkaredis.model.UserData;

public interface KafkaProducerService {
    void sendToKafka(UserData user);
}
