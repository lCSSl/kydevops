package com.kaiyu.aop.annotation;



import com.kaiyu.aop.service.ApiService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: ysbian
 * @date: 2019/11/5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ServiceHandler {
    String value() default "";

    Class<? extends ApiService> serviceClass() default DEFAULT.class;

    boolean async() default true;

    class DEFAULT implements ApiService<Object, Object> {
        @Override
        public Object handle(Object o) {
            return null;
        }
    }
}
