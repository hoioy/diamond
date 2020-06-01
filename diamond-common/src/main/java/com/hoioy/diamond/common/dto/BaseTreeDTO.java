package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class BaseTreeDTO<T extends BaseTreeDTO> extends BaseDTO<T> {
    @ApiModelProperty(value = "parent id")
    private String parentId;
    @ApiModelProperty(value = "树型结构DTO子对象", hidden = true)
    private List<T> children;
}