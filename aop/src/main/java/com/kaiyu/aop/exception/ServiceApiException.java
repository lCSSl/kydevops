package com.kaiyu.aop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author: ysbian
 * @date: 2019-10-21
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum ServiceApiException implements ApiExceptionInventory {
    CAPTCHA_EXPIRED(2, BAD_REQUEST),
    CLIENT_ERROR(4, BAD_REQUEST),
    CLIENT_ERROR_POSITION(4, BAD_REQUEST),
    COLLECT_TIMEOUT(5, BAD_REQUEST),
    CAPTCHA_BAD_CODE(2000, BAD_REQUEST),
    SECURITY_CODE_TIMEOUT(2001, BAD_REQUEST),
    SECURITY_CODE_HAS_USED(2002, BAD_REQUEST),
    DATA_INVALID(65537, BAD_REQUEST),
    DIRTY_WRITE(65536, INTERNAL_SERVER_ERROR),
    ;
    private Integer code;
    private HttpStatus httpStatus;


    @Component
    public static class MessageSourceGenerator {

        public void postConstruct() throws Exception {
            ClassPathResource resource = new ClassPathResource("locale/messages_zh_CN.properties");

            boolean dirty = false;
            Properties props = new Properties();

            try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
                props.load(reader);
                props.stringPropertyNames().stream()
                        .forEach(k -> log.debug(k + "=" + props.getProperty(k)));


                for (ServiceApiException rt : EnumSet.allOf(ServiceApiException.class)) {
                    String key = rt.getClass().getName() + "." + rt.toString();
                    if (null == props.getProperty(key)) {
                        props.setProperty(key, "{0}");
                        log.debug("added:{}", key);
                        dirty = true;
                    }
                }
            }

            if (dirty) {

                Map<String, String> treeMap = new TreeMap<>(
                        Comparator.naturalOrder()
                );
                props.entrySet().forEach(e -> treeMap.put(e.getKey().toString(), e.getValue().toString()));
                try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(resource.getFile()))) {
                    treeMap.entrySet().forEach(t -> {
                        try {
                            osw.write(t.getKey() + "=" + t.getValue() + "\n");
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                }
            }
        }
    }
}
