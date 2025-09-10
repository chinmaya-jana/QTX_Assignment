package com.devapp.redisOP.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.event.connection.DisconnectedEvent;
import io.lettuce.core.event.connection.ConnectedEvent;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password:}") // optional
    private String redisPassword;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        ClientResources clientResources = DefaultClientResources.create();

        // Subscribe to Redis connection events
        clientResources.eventBus().get().subscribe(event -> {
            if (event instanceof DisconnectedEvent) {
                log.warn("Redis connection lost!");
            } else if (event instanceof ConnectedEvent) {
                log.info("Redis connection established/reconnected");
            }
        });

        return clientResources;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        if (redisPassword != null && !redisPassword.isBlank()) {
            standaloneConfig.setPassword(redisPassword);
        }

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientResources(clientResources)
                .commandTimeout(Duration.ofMillis(200)) // fail fast
                .shutdownTimeout(Duration.ZERO)
                .build();

        // Validate at startup (fail app if Redis is not reachable)
        RedisURI redisURI = RedisURI.Builder.redis(redisHost, redisPort)
                .withTimeout(Duration.ofMillis(200))
                .build();
        if (redisPassword != null && !redisPassword.isBlank()) {
            redisURI.setPassword(redisPassword.toCharArray());
        }
        try (RedisClient client = RedisClient.create(redisURI)) {
            client.connect().close();
            log.info("Initial Redis connection successful");
        } catch (Exception e) {
            log.error("Cannot connect to Redis at startup: {}", e.getMessage());
            throw new IllegalStateException("Redis must be running before starting the application");
        }

        return new LettuceConnectionFactory(standaloneConfig, clientConfig);
    }
}
