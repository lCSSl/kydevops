package com.kaiyu.aop.bean.base;

import com.kaiyu.aop.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author antelope
 */
@Getter
@Setter
public class AbstractOutputBean extends JsonUtils {

    /**
     * 调用接口的返回码，调用成功为0。可以为空。
     */
    private Integer errCode = 0;

    /**
     * 调用接口的返回信息，可以为空。
     */
    private String errMsg;

    /**
     * 如果用户的token无效，则为用户生成一个新的token。需与errCode配合使用。
     */
    private String token;

    /**
     * 用Object处理返回的数据
     */
    private Object data;
}
