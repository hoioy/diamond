package com.hoioy.diamond.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@ApiModel(value = "批量保存操作DTO", description = "批量保存操作DTO")
@Data
@NoArgsConstructor
public class BatchSaveDTO<DTO extends CommonDTO> implements Serializable {
    private static final long serialVersionUID = 11L;

    @ApiModelProperty(value = "要删除的关联关系DTO集合")
    List<DTO> deleteDTOs;

    @ApiModelProperty(value = "要新增的关联关系DTO集合")
    List<DTO> createDTOs;
}