package com.hoioy.sample.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teacher")
@ApiModel(value = "Teacher对象", description = "")
public class Teacher {

    private static final long serialVersionUID = 1L;

    @TableId("pk")
    private String pk;

    private String teacherName;

    private String teacherSex;

    private Integer flag;

}
