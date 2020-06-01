package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "分页查询排序字段对象", description = "分页查询排序字段对象")
@Data
@NoArgsConstructor
public final class SortDTO implements Serializable{
    private static final long serialVersionUID = 11L;

    @ApiModelProperty(value = "需要排序的属性名称")
    private String fieldName;

    @ApiModelProperty(value = "ASC或者DESC",example = "asc")
    private String direction;

}
