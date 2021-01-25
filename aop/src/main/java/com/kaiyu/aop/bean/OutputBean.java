package com.kaiyu.aop.bean;

import com.kaiyu.aop.bean.base.AbstractOutputBean;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OutputBean extends AbstractOutputBean {
    private int a;
}
