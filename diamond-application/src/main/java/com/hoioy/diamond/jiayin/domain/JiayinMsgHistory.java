package com.hoioy.diamond.jiayin.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="JiayinMsgHistory对象", description="观看历史")
public class JiayinMsgHistory extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 消息表主键键
     */
    private String msgId;

    /**
     * 用户id
     */
    private String userId;


}
