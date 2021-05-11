package com.hoioy.sample.dto;

import com.hoioy.diamond.common.dto.BaseJoinDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CourseStudentScore对象", description="")
public class CourseStudentScoreDTO  extends BaseJoinDTO implements Serializable {
    private String studentId;

    private String courseId;

    private String score;
}
