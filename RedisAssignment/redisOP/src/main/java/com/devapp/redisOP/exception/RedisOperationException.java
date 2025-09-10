package com.devapp.redisOP.exception;

public class RedisOperationException extends RuntimeException{
    public RedisOperationException(String message) {
        super(message);
    }
}
