package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "分页查询对象", description = "分页查询对象")
@Data
@NoArgsConstructor
public class PageDTO<DTO extends CommonDTO> extends CommonPageDTO<DTO> implements Serializable {

}
