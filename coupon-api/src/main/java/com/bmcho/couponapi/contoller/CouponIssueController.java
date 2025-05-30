package com.bmcho.couponapi.contoller;

import com.bmcho.couponapi.dto.CouponIssueRequestDto;
import com.bmcho.couponapi.dto.CouponIssueResponseDto;
import com.bmcho.couponapi.service.CouponIssueRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CouponIssueController {

    private final CouponIssueRequestService couponIssueRequestService;


    @GetMapping("/v1/health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issueV1(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.issueRequestV1(body);
        return new CouponIssueResponseDto(true, null);
    }

    @PostMapping("/v1/issue-async")
    public CouponIssueResponseDto issueAsyncV1(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.asyncIssueRequestV1(body);
        return new CouponIssueResponseDto(true, null);
    }

    @PostMapping("/v2/issue-async")
    public CouponIssueResponseDto issueAsyncV2(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.asyncIssueRequestV2(body);
        return new CouponIssueResponseDto(true, null);
    }
}
