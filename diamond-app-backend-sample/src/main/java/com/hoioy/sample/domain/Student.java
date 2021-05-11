package com.hoioy.sample.domain;

import com.hoioy.diamond.common.base.BaseDomain;
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
@TableName("student")
@ApiModel(value="Student对象", description="")
public class Student extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生班级
     */
    private String studentClass;

    /**
     * 学生性别
     */
    private String studentSex;

}
