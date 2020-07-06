package com.hoioy.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 信息表
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Message对象", description = "信息表")
public class MessageDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "小程序openid")
    private String openid;
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "信息类型表外键")
    private String msgTypeId;

    @ApiModelProperty(value = "信息类型名称")
    private String msgTypeName;

    @ApiModelProperty(value = "信息类型主题色")
    private String msgTypeColor;

    @ApiModelProperty(value = "信息内容")
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

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "县")
    private String country;
    @ApiModelProperty(value = "镇")
    private String town;
    @ApiModelProperty(value = "村")
    private String village;

    @ApiModelProperty(value = "省Name")
    private String provinceName;
    @ApiModelProperty(value = "市Name")
    private String cityName;
    @ApiModelProperty(value = "县Name")
    private String countryName;
    @ApiModelProperty(value = "镇Name")
    private String townName;
    @ApiModelProperty(value = "村Name")
    private String villageName;

    @ApiModelProperty(value = "所属用户名称")
    private String userName;
    @ApiModelProperty(value = "所属用户头像")
    private String avatar;
}
