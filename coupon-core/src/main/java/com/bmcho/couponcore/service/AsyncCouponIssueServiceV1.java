package com.bmcho.couponcore.service;

import com.bmcho.couponcore.component.DistributeLockExecutor;
import com.bmcho.couponcore.exception.CouponIssueException;
import com.bmcho.couponcore.exception.ErrorCode;
import com.bmcho.couponcore.model.Coupon;
import com.bmcho.couponcore.model.CouponIssue;
import com.bmcho.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.bmcho.couponcore.repository.mysql.CouponIssueRepository;
import com.bmcho.couponcore.repository.mysql.CouponJpaRepository;
import com.bmcho.couponcore.repository.redis.RedisRepository;
import com.bmcho.couponcore.repository.redis.dto.CouponIssueRequest;
import com.bmcho.couponcore.util.CouponRedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final CouponIssueService couponIssueService;
    private final ObjectMapper objectMapper;

    private final DistributeLockExecutor distributeLockExecutor;

    public void issue(long couponId, long userId) {
//        /*
//            1. 유저 요청 sorted set 적재
//            2. 유저 요청 순서 조회
//            3. 조회 결과 선착순 조건 비교
//            4. 쿠폰 발급 큐 적재
//         */
//        String key = "issue.request.sorted_set.couponId=%s".formatted(couponId);
//        redisRepository.zAdd(key, String.valueOf(userId), System.currentTimeMillis());

        Coupon coupon = couponIssueService.findCoupon(couponId);
        if (coupon.availableIssueDate()) {
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE,
                "발급 가능한 일자가 아닙니다.. couponId : %s, issueStart : %s, issueEnd: %s".formatted(couponId, coupon.getDateIssueStart(), coupon.getDateIssueEnd()));
        }

        distributeLockExecutor.execute("lock_%s".formatted(couponId), 3000, 3000,
            () -> {
                if (!couponIssueRedisService.availableTotalIssueQuantity(coupon.getTotalQuantity(), couponId)) {
                    throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY,
                        "발급 가능한 수량을 초과합니다. couponId : %s, userId: %s".formatted(couponId, userId));
                }
                if (couponIssueRedisService.availableUserIssueQuantity(couponId, userId)) {
                    throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE,
                        "이미 발급 요청이 처리되었습니다. couponId : %s, userId: %s".formatted(couponId, userId));
                }
                issueRequest(couponId, userId);
            });
    }

    private void issueRequest(long couponId, long userId) {
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(couponId, userId);
        redisRepository.sAdd(CouponRedisUtils.getIssueRequestKey(couponId), String.valueOf(userId));

        //쿠폰 발급 큐 적재
        try {
            redisRepository.rPush(CouponRedisUtils.getIssueRequestQueueKey(), objectMapper.writeValueAsString(couponIssueRequest));
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(ErrorCode.FAIL_COUPON_ISSUE_REQUEST, "input : %s".formatted(couponIssueRequest));
        }
    }
}