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
 * 消息次数
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_msg_count")
@ApiModel(value="MsgCount对象", description="消息次数")
public class MsgCount extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 剩余次数
     */
    private Integer number;


}
