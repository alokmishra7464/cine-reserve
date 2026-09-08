package com.cinereserve.cine_reserve.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class RedisLockService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisLockService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String acquireLock(Long showId, Long seatId) {

        String key = "show:" + showId + ":seat:" + seatId;
        String value = UUID.randomUUID().toString();

        Boolean acquired = stringRedisTemplate.opsForValue()
                .setIfAbsent(key, value, Duration.ofSeconds(10));

        if(Boolean.TRUE.equals(acquired)) {
            return value;
        }

        return null;
    }

    public void releaseLock(Long showId, Long seatId, String value) {
        String key = "show:" + showId + ":seat:" + seatId;
        String currentValue = stringRedisTemplate.opsForValue()
                .get(key);

        String script = """
                if redis.call('get', KEYS[1]) == ARGV[1] then
                    return redis.call('del', KEYS[1])
                    
                else 
                    return 0
                
                end
                """;

        stringRedisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                List.of(key),
                value
        );
    }
}
