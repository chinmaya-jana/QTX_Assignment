package com.devapp.redisOP.controller;

import com.devapp.redisOP.response.ApiResponse;
import com.devapp.redisOP.service.RedisService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/redis")
@Validated
@AllArgsConstructor
public class RedisController {
    private final RedisService redisService;

    // POST /api/redis/set?key=name&value=John
    @PostMapping("/set")
    public ResponseEntity<ApiResponse<Map<String, String>>> set(
            @RequestParam @NotBlank(message = "Key cannot be blank") String key,
            @RequestParam @NotBlank(message = "Value cannot be blank") String value) {
        Map<String, String> response = redisService.set(key, value);

        return ResponseEntity.ok(new ApiResponse<>(true, "Key set successfully", response));
    }

    // GET /api/redis/get?key=name
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<Map<String, String>>> get(
            @RequestParam @NotBlank(message = "Key cannot be blank") String key) {
        Map<String, String> response = redisService.get(key);
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Key not found in Redis", Map.of())
            );
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Key fetched successfully", response));
    }

    // DELETE /api/redis/delete?key=name
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Map<String, String>>> delete(
            @RequestParam @NotBlank(message = "Key cannot be blank") String key) {
        Map<String, String> response = redisService.delete(key);
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Key not found in Redis", Map.of())
            );
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Key deleted successfully", response));
    }

    // POST /api/redis/lpush?key=mylist&value=first
    @PostMapping("/lpush")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> lpush(
            @RequestParam @NotBlank(message = "Key cannot be blank") String key,
            @RequestParam @NotBlank(message = "Value cannot be blank") String value) {
        Map<String, List<String>> response = redisService.lpush(key, value);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Key set successfully", response));
    }

    //POST /api/redis/rpush?key=mylist&value=last
    @PostMapping("/rpush")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> rpush(
            @RequestParam @NotBlank(message = "Key cannot be blank") String key,
            @RequestParam @NotBlank(message = "Value cannot be blank") String value) {
        Map<String, List<String>> response = redisService.rpush(key, value);

        return ResponseEntity.ok(new ApiResponse<>(true, "Key set successfully", response));
    }

    //GET /api/redis/list?key=mylist
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> getList(
            @RequestParam @NotBlank(message = "Key cannot be blank") String key) {
        Boolean exists = redisService.exists(key);
        if (Boolean.FALSE.equals(exists)) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Key not found in Redis", Map.of())
            );
        }
        Map<String, List<String>> response = redisService.getList(key);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Key fetch successfully", response));
    }
}
