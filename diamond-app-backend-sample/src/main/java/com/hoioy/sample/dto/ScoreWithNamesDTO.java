package com.hoioy.sample.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "演示复合查询DTO，分数，包含课程名称和学生名称等 DTO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreWithNamesDTO extends BaseDTO {
    private static final long serialVersionUID = 2533428876699L;

    private String score;
    private String studentId;
    private String studentName;
    private String studentClass;
    private String studentSex;
    private String courseId;
    private String courseName;
}
