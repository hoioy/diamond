package com.hoioy.diamond.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_msg_type")
@ApiModel(value="MsgType对象", description="消息类型")
public class MsgType extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 价格
     */
    private String money;

    /**
     * 有效期
     */
    private Integer expiryDate;


}
