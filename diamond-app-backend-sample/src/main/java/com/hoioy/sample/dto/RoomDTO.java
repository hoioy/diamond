package com.hoioy.sample.dto;

import com.hoioy.diamond.common.dto.CommonDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * 教室管理DTO, 继承CommonXXX,弱约束方式
 **/
@ApiModel(value = "RoomDTO对象")
@Data
@NoArgsConstructor
public class RoomDTO extends CommonDTO {
    private static final long serialVersionUID = 255881111428876699L;

    private String pk;

    @Size(min = 2, max = 20, message = "学生班级长度只能在2-20之间")
    private String roomName;

    private String roomDesc;

    private Integer flag;
}
