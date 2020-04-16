package com.hoioy.diamond.jiayin.domain;

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
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="JiayinMsgType对象", description="消息类型")
public class JiayinMsgType extends BaseDomain {

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
