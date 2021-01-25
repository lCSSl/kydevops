package com.kaiyu.devops.blog.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaiyu.devops.entities.Replay;

import java.util.List;

/**
 * <p>
 * 回答信息表 Mapper 接口
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 */
public interface ReplayMapper extends BaseMapper<Replay> {

    /**
     * 通过问题id递归查询所有回答信息
     * @return
     */
    List<Replay> findByQuestionId(String questionId);
}
