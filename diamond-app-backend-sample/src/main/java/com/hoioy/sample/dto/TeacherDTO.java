package com.hoioy.sample.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * 类描述：不继承，JPA原始写法，老师管理DTO对象
 **/
@ApiModel(value = "Teacher对象")
@Data
@NoArgsConstructor
public class TeacherDTO {
    private static final long serialVersionUID = 2558887945428876699L;

    private String pk;

    @Size(min = 2, max = 20, message = "姓名长度只能在2-20之间")
    private String teacherName;

    private String teacherSex;

    private Integer flag;
}
