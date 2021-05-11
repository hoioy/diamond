package com.hoioy.sample.domain;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.base.BaseJoinDomain;
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
@TableName("course_student_score")
@ApiModel(value="CourseStudentScore对象", description="")
public class CourseStudentScore extends BaseJoinDomain {

    private static final long serialVersionUID=1L;

    @BaseJoinId(index=BaseJoinId.Index.first)
    private String studentId;

    @BaseJoinId(index=BaseJoinId.Index.second)
    private String courseId;

    private String score;

}
