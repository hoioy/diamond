package com.hoioy.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>
 * 消息草稿
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_msg_draft")
@ApiModel(value="MsgDraft对象", description="消息草稿")
public class MsgDraft extends BaseDomain {

    private static final long serialVersionUID=1L;
    private String openid;
    /**
     * 标题
     */
    private String title;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * (带交易 已完成)
     */
    private Integer status;

    /**
     * 有效期
     */
    private LocalDate expareTime;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactPhone;


}
