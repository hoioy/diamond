package com.hoioy.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 观看历史
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="MsgHistory对象", description="观看历史")
public class MsgHistoryDTO  extends BaseDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "消息表主键键")
    private String msgId;


    @ApiModelProperty(value = "用户id")
    private String userId;



}
