package com.bmcho.couponcore.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void zAdd(String key, String value, double score) {
        redisTemplate.opsForZSet().addIfAbsent(key, value, score);
    }

}