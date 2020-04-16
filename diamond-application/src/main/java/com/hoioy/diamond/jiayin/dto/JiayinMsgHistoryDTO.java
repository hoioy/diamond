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
 * 观看历史
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="JiayinMsgHistory对象", description="观看历史")
public class JiayinMsgHistoryDTO  extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "消息表主键键")
    private String msgId;


    @ApiModelProperty(value = "用户id")
    private String userId;



}
