package com.hoioy.sample.dto;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import lombok.Data;

@Data
public class SchoolGradeDTO extends BaseTreeDTO {


    private String gradeName;

    private String name;

    private String state;
}