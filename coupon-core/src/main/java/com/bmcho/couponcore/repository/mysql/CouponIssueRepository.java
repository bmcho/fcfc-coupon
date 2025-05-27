package com.bmcho.couponcore.repository.mysql;

import com.bmcho.couponcore.model.CouponIssue;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.bmcho.couponcore.model.QCouponIssue.couponIssue;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<CouponIssue> findFirstCouponIssue(long couponId, long userId) {
        return Optional.ofNullable(queryFactory.selectFrom(couponIssue)
            .where(couponIssue.couponId.eq(couponId))
            .where(couponIssue.userId.eq(userId))
            .fetchFirst());
    }
}
