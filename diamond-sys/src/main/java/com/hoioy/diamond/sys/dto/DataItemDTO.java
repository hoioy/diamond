package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 类名称：数据字典dto
 */
@Data
@NoArgsConstructor
public class DataItemDTO extends BaseDTO {

    private static final long serialVersionUID = 7275727535778417507L;
    @NotBlank(message = "code不能为空")
    private String code;

    private Integer codeIndex;

    private String name;

    //是否启用:0不启用
    private String state;

    //TODO zhaozhao type和category 在优化tdf-sys-web时候删除
    private String type;
    private Integer category;

}
