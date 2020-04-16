package com.hoioy.diamond.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="JiayinMessage对象", description="消息表")
public class JiayinMessageDTO  extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "标题")
    private String title;


    @ApiModelProperty(value = "消息类型")
    private Integer msgType;


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
