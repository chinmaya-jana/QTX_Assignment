package com.devapp.kafkaredis.controller;

import com.devapp.kafkaredis.exception.ResourceNotFoundException;
import com.devapp.kafkaredis.model.UserData;
import com.devapp.kafkaredis.request.UserDataRequest;
import com.devapp.kafkaredis.service.KafkaProducerService;
import com.devapp.kafkaredis.service.RedisService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final RedisService redisService;
    private final KafkaProducerService kafkaProducerService;

    // 1. Save Data to Redis
    @PostMapping("/redis")
    public ResponseEntity<String> saveToRedis(@Valid @RequestBody UserDataRequest request) {
        redisService.save(request);
        return ResponseEntity.ok("Saved to Redis");
    }

    // 2. Publish Redis Data to Kafka
    @PostMapping("/publish/{id}")
    public ResponseEntity<String> publishToKafka(@PathVariable("id") String userId) {
        UserData user = redisService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("No data found in Redis for ID: " + userId);
        }
        kafkaProducerService.sendToKafka(user);
        return ResponseEntity.ok("Data sent to Kafka from Redis");
    }
}
