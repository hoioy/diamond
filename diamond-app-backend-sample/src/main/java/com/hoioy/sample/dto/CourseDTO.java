package com.hoioy.sample.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Course对象", description="")
public class CourseDTO  extends BaseDTO implements Serializable {

    @Size(min = 2, max = 20, message = "课程名称长度只能在2-20之间")
    private String courseName;

    private String score;

}
