package com.kaiyu.aop.config;

import com.google.gson.Gson;
import com.kaiyu.aop.executor.ContextAwarePoolExecutor;
import com.kaiyu.aop.utils.JsonUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.lang.management.ManagementFactory;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.Executor;

/**
 * @author: ysbian
 * @date: 2019/11/5
 */
@Configuration
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@MapperScan({"com.kaiyu.aop"})
@ComponentScan("com.kaiyu.aop.service")
public class ApiConfiguration implements WebMvcConfigurer {
    public static String HOSTNAME;
    public static String PID;

    static {
        int idx = ManagementFactory.getRuntimeMXBean().getName().indexOf('@');

        if (idx < 0) {
            HOSTNAME = "unknown";
            PID = "0";
        } else {
            PID = ManagementFactory.getRuntimeMXBean().getName().substring(0, idx);
            HOSTNAME = ManagementFactory.getRuntimeMXBean().getName().substring(idx + 1);
        }
    }

    @Bean
    public Gson gson() {
        return JsonUtils.GSON;
    }

    @Bean
    public LocaleContextResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        resolver.setCookieName("ctx-locale");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setBasename("classpath:locale/messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        registry.addInterceptor(interceptor);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }


    @Bean(name = "async-service")
    public Executor workerExecutor() {
        ContextAwarePoolExecutor executor = new ContextAwarePoolExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix(new StringJoiner("-").add(HOSTNAME.replace('-', '_')).add(PID).add("service-").toString());
        return executor;
    }

}
