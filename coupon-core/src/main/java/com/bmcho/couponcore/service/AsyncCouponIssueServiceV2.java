package com.bmcho.couponcore.service;

import com.bmcho.couponcore.repository.redis.RedisRepository;
import com.bmcho.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV2 {

    private final RedisRepository redisRepository;
    private final CouponCacheService couponCacheService;

    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponLocalCache(couponId);
        coupon.checkIssuableCoupon();
        issueRequest(couponId, userId, coupon.totalQuantity());
    }

    public void issueRequest(long couponId, long userCouponId, Integer totalIssueQuantity) {
        if (totalIssueQuantity == null) {
            redisRepository.issueRequest(couponId, userCouponId, Integer.MAX_VALUE);
        } else {
            redisRepository.issueRequest(couponId, userCouponId, totalIssueQuantity);
        }
    }

}