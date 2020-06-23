package com.hoioy.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="PublishHistory对象", description="")
public class PublishHistoryDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "发布主键键")
    private String publishId;


    @ApiModelProperty(value = "openid")
    private String openid;


    @ApiModelProperty(value = "发布类型")
    private String publishType;


    @ApiModelProperty(value = "发布标题")
    private String publishTitle;


    @ApiModelProperty(value = "消息类型主键")
    private String msgTypeId;

    @ApiModelProperty(value = "消息类型名字")
    private String msgTypeName;

    @ApiModelProperty(value = "消息类型主题色")
    private String msgTypeColor;

}
