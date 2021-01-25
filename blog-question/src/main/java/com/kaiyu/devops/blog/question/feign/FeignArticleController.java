package com.kaiyu.devops.blog.question.feign;

import com.kaiyu.devops.feign.IFeignQuestionController;
import com.kaiyu.devops.feign.req.UserInfoREQ;
import com.kaiyu.devops.blog.question.service.IQuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "被远程调用的问题微服务接口", description = "被远程调用的问题微服务接口")
@RestController
public class FeignArticleController implements IFeignQuestionController {

    @Autowired
    private IQuestionService questionService;

    @Override
    public boolean updateUserInfo(UserInfoREQ req) {
        return questionService.updateUserInfo(req);
    }
}
