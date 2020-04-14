package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public abstract class TDFDTO implements Serializable {
    @ApiModelProperty(value = "long id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String id;
}
