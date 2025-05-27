package com.bmcho.couponcore.repository.mysql;

import com.bmcho.couponcore.model.Coupon;
import com.bmcho.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}
