package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DataItemTypeDTO extends BaseDTO {

    private static final long serialVersionUID = 72756507L;
    @NotBlank(message = "类型名称不能为空")
    private String typeName;
}
