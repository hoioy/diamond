package com.hoioy.sample.dto;

import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Student对象", description="")
public class StudentDTO  extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "学生姓名")
    @Size(min = 2, max = 20, message = "学生姓名长度只能在2-20之间")
    private String studentName;

    @ApiModelProperty(value = "学生班级")
    @Size(min = 2, max = 20, message = "学生班级长度只能在2-20之间")
    private String studentClass;

    @ApiModelProperty(value = "学生性别")
    private String studentSex;
}
