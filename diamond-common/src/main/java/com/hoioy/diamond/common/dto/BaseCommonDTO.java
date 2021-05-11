package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class BaseCommonDTO extends CommonDTO {
    @ApiModelProperty(value = "String UUID", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String id;
}
