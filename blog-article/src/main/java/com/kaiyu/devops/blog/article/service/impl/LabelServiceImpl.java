package com.kaiyu.devops.blog.article.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaiyu.devops.blog.article.mapper.LabelMapper;
import com.kaiyu.devops.blog.article.req.LabelREQ;
import com.kaiyu.devops.entities.Label;
import com.kaiyu.devops.blog.article.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaiyu.devops.util.base.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    @Override
    public Result queryPage(LabelREQ req) {
        // 条件分页查询
        IPage<Label> page = baseMapper.queryPage(req.getPage(), req);
        return Result.ok(page);
    }

    @Override
    public boolean updateById(Label label) {
        label.setUpdateDate(new Date());
        return super.updateById(label);
    }
}
