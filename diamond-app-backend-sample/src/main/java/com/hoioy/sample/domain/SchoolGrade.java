package com.hoioy.sample.domain;

import com.hoioy.diamond.common.base.BaseTreeDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("school_grade")
@ApiModel(value="Course对象", description="")
public class SchoolGrade extends BaseTreeDomain {

    private String gradeName;

    private String name;

    private String state;
}