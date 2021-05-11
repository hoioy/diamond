package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDTO<T extends BaseDTO> extends BaseCommonDTO {
    @ApiModelProperty(value = "创建人", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected String createdBy;
    @ApiModelProperty(value = "创建时间", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime createdDate;
    @ApiModelProperty(value = "修改人", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected String modifiedBy;
    @ApiModelProperty(value = "修改时间", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime modifiedDate;
    @ApiModelProperty(value = "逻辑删除标志。0是已删除", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer flag;
    @ApiModelProperty(value = "备注")
    private String remark;
//    @ApiModelProperty(value = "乐观锁", hidden = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
//    private Integer version;
    //TODO zhaozhao重构前端和tdf-sys-web时候，将前后端的token都删除，只保留version
    @ApiModelProperty(value = "乐观锁2", hidden = true, accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String token;

//    public void setVersion(Integer version) {
//        if (version != null) {
//            this.token = "" + version;
//        }
//        this.version = version;
//    }

//    public void setToken(String token) {
//        if (token != null) {
//            this.version = Integer.parseInt(token);
//        }
//        this.token = token;
//    }
}
