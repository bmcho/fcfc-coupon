package com.bmcho.couponconsumer.component;

import com.bmcho.couponcore.repository.redis.RedisRepository;
import com.bmcho.couponcore.repository.redis.dto.CouponIssueRequest;
import com.bmcho.couponcore.service.CouponIssueService;
import com.bmcho.couponcore.util.CouponRedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
@RequiredArgsConstructor
public class CouponIssueListener {

    private final RedisRepository redisRepository;
    private final CouponIssueService couponIssueService;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000L)
    public void issue() throws JsonProcessingException {
        log.info("issue listen");
        while (existCouponIssueTarget()) {
            CouponIssueRequest target = getIssueTarget();
            log.info("발급 시작 target: " + target);
            couponIssueService.issue(target.couponId(), target.userId());
            log.info("발급 완료 target: " + target);
            removeIssuedTarget();
        }
    }

    private boolean existCouponIssueTarget() {
        return redisRepository.lSize(CouponRedisUtils.getIssueRequestQueueKey()) > 0;
    }

    private CouponIssueRequest getIssueTarget() throws JsonProcessingException {
        return objectMapper.readValue(redisRepository.lIndex(CouponRedisUtils.getIssueRequestQueueKey(), 0), CouponIssueRequest.class);
    }

    private void removeIssuedTarget() {
        redisRepository.lPop(CouponRedisUtils.getIssueRequestQueueKey());
    }

}
