package com.kaiyu.aop.controller;

import com.kaiyu.aop.annotation.ServiceHandler;
import com.kaiyu.aop.bean.OutputBean;
import com.kaiyu.aop.bean.base.AbstractInputBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "aaa", produces = MediaType.APPLICATION_JSON_VALUE)
    @ServiceHandler("serviceImpl.outputBean")
    public OutputBean outputBean(AbstractInputBean inputBean) {
        return null;
    }
}
