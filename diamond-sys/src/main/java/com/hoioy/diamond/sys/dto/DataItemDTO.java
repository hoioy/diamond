package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 类名称：数据字典dto
 */
@Data
@NoArgsConstructor
public class DataItemDTO extends BaseTreeDTO {

    private static final long serialVersionUID = 7275727535778417507L;

    @NotBlank(message = "字典编码不能为空")
    @ApiModelProperty(value = "字典编码，本字段不可为null")
    private String code;

    @ApiModelProperty(value = "字典排序")
    private Integer codeIndex;

    @NotBlank(message = "字典名称不能为空")
    @ApiModelProperty(value = "字典名称")
    private String name;

    @NotBlank(message = "是否启用不能为空")
    @ApiModelProperty(value = "是否启用:0不启用 1启用", name = "state", example = "1")
    private String state;

    @ApiModelProperty(value = "字典类型,如果不使用字典类型，设置为空即可")
    private String dataItemTypeId;

    @ApiModelProperty(value = "字典类型名称,如果不使用字典类型，设置为空即可")
    private String dataItemTypeName;

    public DataItemDTO(String id, String parentId, String code, Integer codeIndex, String name, String state, String dataItemTypeId, String dataItemTypeName, String path) {
        super.setId(id);
        super.setParentId(parentId);
        this.code = code;
        this.codeIndex = codeIndex;
        this.name = name;
        this.state = state;
        this.dataItemTypeId = dataItemTypeId;
        this.dataItemTypeName = dataItemTypeName;
        super.setPath(path);
    }
}
