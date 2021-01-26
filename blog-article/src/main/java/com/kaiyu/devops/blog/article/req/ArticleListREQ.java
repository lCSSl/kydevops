package com.kaiyu.devops.blog.article.req;

import com.kaiyu.devops.entities.Article;
import com.kaiyu.devops.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章列表查询条件
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ArticleListREQ对象", description = "文章列表查询条件")
public class ArticleListREQ extends BaseRequest<Article> {

    /**
     *
     */
    private static final long serialVersionUID = -3877579422270774012L;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "标签ID")
    private String labelId;
}
