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
@ApiModel(value = "AdvertDTO", description = "广告表s")
public class AdvertDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告文字内容")
    private String content;

    @ApiModelProperty(value = "广告链接")
    private String path;

    @ApiModelProperty(value = "广告图片")
    private String icon;
}
