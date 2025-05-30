package com.bmcho.couponcore;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@ComponentScan
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableAspectJAutoProxy(exposeProxy = true)
public class CouponCoreConfiguration {
}
