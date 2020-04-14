package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel(value = "分页查询对象", description = "分页查询对象")
@Data
@NoArgsConstructor
public class PageDTO implements Serializable {

    //    @Min(value=1, message="页数大于等于1")
    @ApiModelProperty(example = "1")
    private int page;
    //    @Min(value=1, message="每页最少展示1项")
//    @Max(value=1000, message="每页最多展示1000项")
    @ApiModelProperty(example = "10")
    private int pageSize;

    private List<Map> sorts;

    private List<Map> filters;

    private Long total;

    private List list;
}
