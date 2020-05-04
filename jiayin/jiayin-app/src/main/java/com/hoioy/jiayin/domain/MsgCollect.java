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
 * 消息收藏
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_msg_collect")
@ApiModel(value="MsgCollect对象", description="消息收藏")
public class MsgCollect extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 消息表主键
     */
    private String msgId;

    /**
     * 用户id
     */
    private String openid;


}
