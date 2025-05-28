package com.bmcho.couponcore.service;

import com.bmcho.couponcore.exception.CouponIssueException;
import com.bmcho.couponcore.exception.ErrorCode;
import com.bmcho.couponcore.model.Coupon;
import com.bmcho.couponcore.model.CouponIssue;
import com.bmcho.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.bmcho.couponcore.repository.mysql.CouponIssueRepository;
import com.bmcho.couponcore.repository.mysql.CouponJpaRepository;
import com.bmcho.couponcore.repository.redis.RedisRepository;
import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;

    public void issue(long couponId, long userId) {
        /*
            1. 유저 요청 sorted set 적재
            2. 유저 요청 순서 조회
            3. 조회 결과 선착순 조건 비교
            4. 쿠폰 발급 큐 적재
         */
        String key = "issue.request.sorted_set.couponId=%s".formatted(couponId);
        redisRepository.zAdd(key, String.valueOf(userId), System.currentTimeMillis());


    }

}