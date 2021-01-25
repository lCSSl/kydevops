package com.kaiyu.aop.aspect;

import com.kaiyu.aop.annotation.ServiceHandler;
import com.kaiyu.aop.bean.base.AbstractInputBean;
import com.kaiyu.aop.exception.ServiceApiException;
import com.kaiyu.aop.service.ApiService;
import com.kaiyu.aop.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author: ysbian
 * @date: 2019/11/5
 */
@Slf4j
public abstract class AbstractControllerAdvice {

    @Autowired
    protected ApplicationContext ctx;
    @Autowired
    @Qualifier("async-service")
    private Executor executor;
    private final String[] exclusionsURIArray = {"/xi/oauth2User"};

    private <T, R> CompletableFuture<R> invokeService(ApiService<? super T, ? extends R> service, T input, boolean async) {
        if (async) {
            return CompletableFuture.supplyAsync(() -> service.handle(input), executor);
        }
        return CompletableFuture.completedFuture(service.handle(input));
    }

    @Pointcut(" @annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)")
    final protected void mappingPointcut() {
    }

    protected abstract void methodPointcut();

    @Around("mappingPointcut() && methodPointcut() && args(param)")
    private Object processController(final ProceedingJoinPoint pjp, AbstractInputBean param) throws Throwable {
        log.debug("Controller Advice start.");
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        final HttpServletRequest request = sra.getRequest();
        final HttpServletResponse response = sra.getResponse();
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        log.debug("Handle Request: {} {} by {}.{},content:\n{}", requestMethod, requestURI, method.getDeclaringClass().getName(), method.getName(), param.toString());
        CompletableFuture<?> result;
        Object[] args = pjp.getArgs();
        before(args, request, response, param);
        ServiceHandler targetService = method.getAnnotation(ServiceHandler.class);
        if (null != targetService) {
            ApiService service = locateService(targetService);
            Objects.requireNonNull(service);
            Object input;
            if (null != args && args.length > 0) {
                input = args[0];
                log.debug("App Input:\n {}", JsonUtils.printJson(input));
            } else {
                input = null;
            }
            result = targetService.async() ? invokeService(service, input, true) :
                    invokeService(service, input, false);
        } else {
            result = (CompletableFuture<?>) pjp.proceed();
        }
        result.whenComplete((o, t) -> {
            if (null != t) {
                log.error(t.getMessage(), t);
            }
            if (null != o) {
                log.debug("App Output:\n {}", JsonUtils.printJson(o));
            }
        });
        log.debug("Controller handled");
        return result;
    }

    private ApiService locateService(ServiceHandler targetService) {
        if (StringUtils.hasText(targetService.value())) {
            return (input) -> {
                final StandardEvaluationContext context = new StandardEvaluationContext();
                context.setBeanResolver(new ServiceBeanResolver(ctx));
                context.setVariable("input", input);
                final SpelExpressionParser parser = new SpelExpressionParser();
                final Expression exp = parser.parseExpression("@" + targetService.value() + "(#input)");
                return exp.getValue(context);
            };
        }
        return ctx.getBean(targetService.serviceClass());
    }

    protected void before(Object[] args, HttpServletRequest request, HttpServletResponse response, AbstractInputBean param) {
        String requestURI = request.getRequestURI();
        for (String exclusionsURI : exclusionsURIArray) {
            if (requestURI.startsWith(exclusionsURI)) {
                return;
            }
        }
        if (null == param || null == param.getToken()) {
            throw ServiceApiException.CLIENT_ERROR.fire("登录过期");
        }

        ((AbstractInputBean) args[0]).setUserId(1);
    }


    static class ServiceBeanResolver implements BeanResolver {

        private ApplicationContext ctx;

        ServiceBeanResolver(ApplicationContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public Object resolve(EvaluationContext context, String beanName) {
            return ctx.getBean(beanName);
        }
    }
}
