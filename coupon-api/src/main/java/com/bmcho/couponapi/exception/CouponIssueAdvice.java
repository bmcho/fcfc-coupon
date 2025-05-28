package com.bmcho.couponapi.exception;

import com.bmcho.couponapi.dto.CouponIssueResponseDto;
import com.bmcho.couponcore.exception.CouponIssueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponIssueAdvice {

    @ExceptionHandler(CouponIssueException.class)
    public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException exception) {
        return new CouponIssueResponseDto(false, exception.getErrorCode().message);
    }
}
