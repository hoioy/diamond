package com.hoioy.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private String openid;

    /**
     * 剩余次数
     */
    private Integer msgCount;


}
