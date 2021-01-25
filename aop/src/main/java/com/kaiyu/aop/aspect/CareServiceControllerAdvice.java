package com.kaiyu.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author: ysbian
 * @date: 2019-10-21
 */
@Aspect
@Slf4j
@Component
public class CareServiceControllerAdvice extends AbstractControllerAdvice {

    /**
     * Method pointcut.
     */
    @Override
    @Pointcut("execution(* com.kaiyu.aop.controller..*.*(..))")
    public void methodPointcut() {
    }


}

