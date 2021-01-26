package com.kaiyu.devops.blog.article.req;

import com.kaiyu.devops.entities.Label;
import com.kaiyu.devops.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(value = "LabelREQ对象", description = "标签查询条件")
@Data
@Accessors(chain = true)
public class LabelREQ extends BaseRequest<Label> {

    /**
	 *
	 */
	private static final long serialVersionUID = 3808976804452743992L;

	@ApiModelProperty(value="标签名称")
    private String name;

    @ApiModelProperty(value="分类id")
    private String categoryId;

}
