package com.kaiyu.aop.exception;



import com.kaiyu.aop.utils.JsonUtils;

import java.io.Serializable;

public interface JsonSerializable extends Serializable {

    default String printJson() {
        return JsonUtils.printJson(this);
    }

    default String toJson() {
        return JsonUtils.toJson(this);
    }

    default byte[] toJsonBin() {
        return JsonUtils.toBin(this);
    }

    default String toJsonBase64() {
        return JsonUtils.toBase64(this);
    }
}
