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
 * 消息表
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Message对象", description="消息表")
public class MessagePageDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "小程序openid")
    private String openid;
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消息类型表外键")
    private String msgTypeId;

    @ApiModelProperty(value = "消息类型名称")
    private String msgTypeName;

    @ApiModelProperty(value = "消息类型主题色")
    private String msgTypeColor;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "浏览次数")
    private Integer views;

    @ApiModelProperty(value = "(带交易 已完成)")
    private Integer status;

    @ApiModelProperty(value = "过期时间")
    private LocalDate expareTime;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

}
