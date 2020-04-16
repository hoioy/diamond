package com.hoioy.diamond.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息次数
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="JiayinMsgCount对象", description="消息次数")
public class JiayinMsgCountDTO  extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    private String userId;


    @ApiModelProperty(value = "剩余次数")
    private Integer number;



}
