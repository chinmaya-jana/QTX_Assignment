package com.devapp.kafkaredis.service.impl;

import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "redis-data-topic";
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    public void sendToKafka(UserData user) {
        kafkaTemplate.send(TOPIC, user.getId(), user);
    }
}
