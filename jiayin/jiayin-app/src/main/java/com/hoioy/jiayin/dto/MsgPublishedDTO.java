package com.hoioy.jiayin.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "published对象", description = "")
public class MsgPublishedDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布主键")
    private String msgId;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "发布类型")
    private String msgTableName;

    @ApiModelProperty(value = "发布标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息类型主键")
    private String msgTypeId;

    @ApiModelProperty(value = "消息类型名字")
    private String msgTypeName;

    @ApiModelProperty(value = "消息类型主题色")
    private String msgTypeColor;

}
