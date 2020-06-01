package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class CommonDTO implements Serializable {
    @ApiModelProperty(value = "long id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String id;
}
