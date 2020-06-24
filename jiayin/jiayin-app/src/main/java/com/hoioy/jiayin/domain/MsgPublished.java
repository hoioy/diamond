package com.hoioy.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_msg_published")
@ApiModel(value = "MsgPublished对象", description = "MsgPublished对象")
public class MsgPublished extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 发布主键键
     */
    private String msgId;

    /**
     * openid
     */
    private String openid;

    /**
     * 发布类型
     */
    private String msgTableName;

    /**
     * 发布标题
     */
    private String msgTitle;

    /**
     * 消息类型主键
     */
    private String msgTypeId;

}
