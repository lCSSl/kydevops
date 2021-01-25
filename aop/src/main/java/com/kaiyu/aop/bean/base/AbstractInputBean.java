package com.kaiyu.aop.bean.base;

import com.kaiyu.aop.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author world
 */
@Getter
@Setter
public class AbstractInputBean extends JsonUtils {
    private int userId;
    private String apiVersion;
    private String token;
    private String openId;
    private String appId;
    private String partnerCode;
    private String payNotifyUrl;
}
