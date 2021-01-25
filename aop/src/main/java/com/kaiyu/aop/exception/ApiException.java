package com.kaiyu.aop.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * @author: ysbian
 * @date: 2019/11/5
 */
@Getter
@Setter
@Builder
public class ApiException extends RuntimeException implements JsonSerializable {

    private Instant timestamp;
    private HttpStatus httpStatus;
    private String messageKey;
    private Integer code;
    private Object[] args;
    private String message;

}
