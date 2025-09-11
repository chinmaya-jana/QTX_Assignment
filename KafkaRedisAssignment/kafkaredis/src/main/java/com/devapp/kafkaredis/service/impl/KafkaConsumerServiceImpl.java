package com.devapp.kafkaredis.service.impl;

import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.service.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    @KafkaListener(topics = "redis-data-topic", groupId = "redis-kafka-group")
    public void consume(UserData user) {
        log.info("Consumed from Kafka: {}", user.toString());
    }
}
