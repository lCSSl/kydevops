package com.kaiyu.aop.executor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * @author: ysbian
 * @date: 2019/11/6
 */
@Slf4j
public class ContextAwareCallable<T> implements Callable<T> {

    private Callable<T> task;
    private RequestAttributes requestContext;
    private LocaleContext localeContext;

    public ContextAwareCallable(Callable<T> task, RequestAttributes requestContext, LocaleContext localeContext) {
        this.task = task;
        this.requestContext = requestContext;
        this.localeContext = localeContext;
    }

    @Override
    public T call() throws Exception {
        if (requestContext != null) {
            RequestContextHolder.setRequestAttributes(requestContext);
        }

        if (localeContext != null) {
            LocaleContextHolder.setLocaleContext(localeContext);
        }

        try {
            return task.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
