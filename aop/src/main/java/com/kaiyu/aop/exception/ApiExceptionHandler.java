package com.kaiyu.aop.exception;

import com.kaiyu.aop.bean.base.AbstractOutputBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author: ysbian
 * @date: 2019/11/6
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private MessageSourceAccessor accessor;

    @ExceptionHandler(value = ApiException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AbstractOutputBean> handleApiException(HttpServletRequest req, ApiException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AbstractOutputBean output = new AbstractOutputBean();

        output.setErrCode(ex.getCode());
        output.setErrMsg(accessor.getMessage(
                ex.getMessageKey(),
                ex.getArgs(),
                ex.getMessage()
        ));

        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = {SQLException.class, IllegalStateException.class, IllegalAccessException.class, NullPointerException.class})
    protected ResponseEntity<Object> handleInternalException(RuntimeException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.debug("Caught internal exception: {}", ex.getMessage());
        AbstractOutputBean output = new AbstractOutputBean();
        output.setErrCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        output.setErrMsg("系统繁忙，请稍后重试");
        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }


    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.debug("bindException : {}",ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON);
        AbstractOutputBean output = new AbstractOutputBean();

        output.setErrCode(9999);
        output.setErrMsg(ex.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }


}
