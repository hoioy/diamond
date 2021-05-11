package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "基础分页查询对象，不依赖Base, 原生JPA或者Mybatis可以直接使用",
        description = "基础查询对象,不依赖Base,原生JPA或者Mybatis可以直接使用")
@Data
@NoArgsConstructor
public class CommonPageDTO<DTO> implements Serializable {

    @Min(value = 1, message = "最少是第一页")
    @ApiModelProperty(example = "1")
    private Integer page;

    @Min(value = 1, message = "每页最少展示1项")
    @Max(value = 1000, message = "每页最多展示1000项")
    @ApiModelProperty(example = "10")
    private Integer pageSize;

    private List<SortDTO> sorts;

    private DTO filters;

    private Long total;

    @ApiModelProperty(example = "[]")
    private List list;
}
