package com.kaiyu.aop.service.impl;

import com.kaiyu.aop.bean.OutputBean;
import com.kaiyu.aop.bean.base.AbstractInputBean;
import com.kaiyu.aop.service.AbstractApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceImpl extends AbstractApiService {
    public OutputBean outputBean(AbstractInputBean inputBean) {
        return new OutputBean().setA(999);
    }
}
