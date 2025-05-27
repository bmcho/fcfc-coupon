package com.bmcho.couponcore.model;

import com.bmcho.couponcore.exception.CouponIssueException;
import com.bmcho.couponcore.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    @DisplayName("발급 가능 수량이 남아 있는 경우 true 반환")
    void availableIssueQuantityTest1() {
        Coupon coupon = Coupon.builder()
            .totalQuantity(100)
            .issuedQuantity(99)
            .build();
        var result = coupon.availableIssueQuantity();
        assertTrue(result);
    }

    @Test
    @DisplayName("발급 가능 수량이 없는 경우 false 반환")
    void availableIssueQuantityTest2() {
        Coupon coupon = Coupon.builder()
            .totalQuantity(100)
            .issuedQuantity(100)
            .build();
        var result = coupon.availableIssueQuantity();
        assertFalse(result);
    }

    @Test
    @DisplayName("발급 가능 수량설정이 없는 경우 true 반환")
    void availableIssueQuantityTest3() {
        Coupon coupon = Coupon.builder()
            .issuedQuantity(100)
            .build();
        var result = coupon.availableIssueQuantity();
        assertTrue(result);
    }

    @Test
    @DisplayName("발급 기한이 시작전인 경우 false 반환")
    void availableIssueDateTest1(){
        Coupon coupon = Coupon.builder()
            .dateIssueStart(LocalDateTime.now().plusDays(1))
            .dateIssueEnd(LocalDateTime.now().plusDays(2))
            .build();
        var result = coupon.availableIssueDate();
        assertFalse(result);
    }

    @Test
    @DisplayName("발급 기한에 해당하는 경우 true 반환")
    void availableIssueDateTest2(){
        Coupon coupon = Coupon.builder()
            .dateIssueStart(LocalDateTime.now().minusDays(1))
            .dateIssueEnd(LocalDateTime.now().plusDays(2))
            .build();
        var result = coupon.availableIssueDate();
        assertTrue(result);
    }

    @Test
    @DisplayName("발급 기한이 지난 경우 false 반환")
    void availableIssueDateTest3(){
        Coupon coupon = Coupon.builder()
            .dateIssueStart(LocalDateTime.now().minusDays(2))
            .dateIssueEnd(LocalDateTime.now().minusDays(1))
            .build();
        var result = coupon.availableIssueDate();
        assertFalse(result);
    }

    @Test
    @DisplayName("발급 성공")
    void issuedTest1(){
        Coupon coupon = Coupon.builder()
            .totalQuantity(100)
            .issuedQuantity(99)
            .dateIssueStart(LocalDateTime.now().minusDays(1))
            .dateIssueEnd(LocalDateTime.now().plusDays(2))
            .build();
        coupon.issue();
        assertEquals(coupon.getIssuedQuantity(),100);
    }

    @Test
    @DisplayName("발급 실패")
    void issuedFalseTest2(){

        // 수량 부족
        Coupon coupon1 = Coupon.builder()
            .totalQuantity(100)
            .issuedQuantity(100)
            .dateIssueStart(LocalDateTime.now().minusDays(1))
            .dateIssueEnd(LocalDateTime.now().plusDays(2))
            .build();

        CouponIssueException exception1 = assertThrowsExactly(CouponIssueException.class, coupon1::issue);
        assertEquals(exception1.getErrorCode(), ErrorCode.INVALID_COUPON_ISSUE_QUANTITY);

        // 발급 시간 x
        Coupon coupon2 = Coupon.builder()
            .totalQuantity(100)
            .issuedQuantity(99)
            .dateIssueStart(LocalDateTime.now().minusDays(2))
            .dateIssueEnd(LocalDateTime.now().minusDays(1))
            .build();

        CouponIssueException exception2 = assertThrowsExactly(CouponIssueException.class, coupon2::issue);
        assertEquals(exception2.getErrorCode(), ErrorCode.INVALID_COUPON_ISSUE_DATE);
    }

}