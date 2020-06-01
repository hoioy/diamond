package com.hoioy.jiayin.dto;


import com.hoioy.diamond.common.dto.BaseTreeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 消息类型
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="MsgType对象", description="消息类型")
public class MsgTypeDTO  extends BaseTreeDTO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "类型名称")
    private String typeName;


    @ApiModelProperty(value = "价格")
    private String money;


    @ApiModelProperty(value = "有效期")
    private Integer expiryDate;



}
