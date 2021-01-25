package com.kaiyu.aop.exception;

import org.springframework.http.HttpStatus;

/**
 * @author: ysbian
 * @date: 2019/11/5
 */
public interface ApiExceptionInventory {
    Integer getCode();

    HttpStatus getHttpStatus();

    default ApiException.ApiExceptionBuilder builder() {
        return ApiException.builder()
                .messageKey(getClass().getName() + "." + toString())
                .code(getCode())
                .httpStatus(getHttpStatus());
    }

    default ApiException fire() {
        return builder().build();
    }

    default ApiException fire(Object... args) {
        return builder().args(args).build();
    }

}