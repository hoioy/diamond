package com.hoioy.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 消息收藏
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="MsgCollect对象", description="消息收藏")
public class MsgCollectDTO  extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "消息表主键")
    private String msgId;


    @ApiModelProperty(value = "小程序openid")
    private String openid;




    @ApiModelProperty(value = "消息类别用来区分不同业务")
    private String messageType;

    @ApiModelProperty(value = "消息标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息类型主键")
    private String msgTypeId;
    @ApiModelProperty(value = "消息类型名称")
    private String msgTypeName;

}
