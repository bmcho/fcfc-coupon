package com.bmcho.couponapi.service;

import com.bmcho.couponcore.component.DistributeLockExecutor;
import com.bmcho.couponapi.dto.CouponIssueRequestDto;
import com.bmcho.couponcore.service.AsyncCouponIssueServiceV1;
import com.bmcho.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;
    private final CouponIssueService couponIssueService;
    private final DistributeLockExecutor distributeLockExecutor;

    public void issueRequestV1(CouponIssueRequestDto requestDto) {
        distributeLockExecutor.execute("lock_" + requestDto.couponId(), 10000, 10000, () -> {
            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
            log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
        });
    }

    public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) {
        asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
    }
}