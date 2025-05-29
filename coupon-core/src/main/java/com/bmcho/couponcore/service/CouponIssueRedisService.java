package com.bmcho.couponcore.service;

import com.bmcho.couponcore.exception.CouponIssueException;
import com.bmcho.couponcore.exception.ErrorCode;
import com.bmcho.couponcore.model.Coupon;
import com.bmcho.couponcore.model.CouponIssue;
import com.bmcho.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.bmcho.couponcore.repository.mysql.CouponIssueRepository;
import com.bmcho.couponcore.repository.mysql.CouponJpaRepository;
import com.bmcho.couponcore.repository.redis.RedisRepository;
import com.bmcho.couponcore.repository.redis.dto.CouponRedisEntity;
import com.bmcho.couponcore.util.CouponRedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponIssueRedisService {

    private final RedisRepository redisRepository;

    public void checkCouponIssueQuantity(CouponRedisEntity couponRedisEntity, long userId) {
        if (!availableTotalIssueQuantity(couponRedisEntity.totalQuantity(), couponRedisEntity.id())) {
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY,
                "발급 가능한 수량을 초과합니다. couponId : %s, userId: %s".formatted(couponRedisEntity.id(), userId));
        }
        if (availableUserIssueQuantity(couponRedisEntity.id(), userId)) {
            throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE,
                "이미 발급 요청이 처리되었습니다. couponId : %s, userId: %s".formatted(couponRedisEntity.id(), userId));
        }
    }

    public boolean availableTotalIssueQuantity(Integer totalQuantity, long couponId) {
        if (totalQuantity == null) return true;

        String key = CouponRedisUtils.getIssueRequestKey(couponId);
        return totalQuantity > redisRepository.sCard(key);
    }

    public boolean availableUserIssueQuantity(long couponId, long userId) {
        String key = CouponRedisUtils.getIssueRequestKey(couponId);
        return redisRepository.sIsMember(key, String.valueOf(userId));
    }

}