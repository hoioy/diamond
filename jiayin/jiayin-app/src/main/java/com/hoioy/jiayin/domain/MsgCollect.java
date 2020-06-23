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
     * 发布主键
     */
    private String msgId;

    /**
     * openid
     */
    private String openid;


    /**
     * 消息类别用来区分不同业务
     */
    private String messageType;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息类型主键
     */
    private String msgTypeId;
}
