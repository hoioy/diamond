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
    private String userId;


}
