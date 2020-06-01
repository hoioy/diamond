package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "分页查询对象", description = "分页查询对象")
@Data
@NoArgsConstructor
public class PageDTO<DTO extends CommonDTO> implements Serializable {

    //    @Min(value=1, message="页数大于等于1")
    @ApiModelProperty(example = "1")
    private int page;
    //    @Min(value=1, message="每页最少展示1项")
//    @Max(value=1000, message="每页最多展示1000项")
    @ApiModelProperty(example = "10")
    private int pageSize;

    private List<SortDTO> sorts;

    private DTO filters;

    private Long total;

    @ApiModelProperty(example = "[]")
    private List list;
}
