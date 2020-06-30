package com.hoioy.jiayin.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseDomain;
import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 信息类型
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
public class FeedBackDTO extends BaseDTO {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "图片路径逗号分隔")
    private String path;

    @ApiModelProperty(value = "联系信息")
    private String contact_info;

    @ApiModelProperty(value = "意见问题")
    private String content;

}
