package com.kaiyu.aop.service;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author: ysbian
 * @date: 2019-10-22
 */
@Slf4j
public abstract class AbstractApiService {
    public final static String SOURCE_CHANNEL_DASHUBAO_WECHAT = "dashubaowechat";
    public static final SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmssSSS");

    public String getUser(String token) {
        return "yes";
    }

    public Integer yuanToFen(BigDecimal amount) {
        return Integer.valueOf(amount.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
    }

    public Integer fenToYuan(BigDecimal amount) {
        return amount.multiply(new BigDecimal(100)).intValue();
    }

    public String generateOrderNumber(char leadingChar) {
        StringBuffer sb = new StringBuffer().append(leadingChar).append("00");
        sb.append(fmt.format(new Date()));
        sb.append(new Random().nextInt(100));
        return sb.toString();
    }

}
