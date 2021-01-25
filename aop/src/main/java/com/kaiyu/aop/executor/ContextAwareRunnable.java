package com.kaiyu.aop.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author: ysbian
 * @date: 2019/11/6
 */
@Slf4j
public class ContextAwareRunnable implements Runnable {
    private Runnable task;
    private RequestAttributes requestContext;
    private LocaleContext localeContext;

    public ContextAwareRunnable(Runnable task, RequestAttributes requestContext, LocaleContext localeContext) {
        this.task = task;
        this.requestContext = requestContext;
        this.localeContext = localeContext;
    }

    @Override
    public void run() {
        if (requestContext != null) {
            RequestContextHolder.setRequestAttributes(requestContext);
        }

        if (localeContext != null) {
            LocaleContextHolder.setLocaleContext(localeContext);
        }

        try {
            task.run();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            RequestContextHolder.resetRequestAttributes();
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
