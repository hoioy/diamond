package com.hoioy.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 消息表
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_message")
@ApiModel(value="Message对象", description="消息表")
public class Message extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 标题
     */
    private String title;

    private String openid;

    /**
     * 消息类型外键
     */
    private String msgTypeId;
    /**
     * 消息类型名称
     */
    @TableField(exist = false)
    private String msgTypeName;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 1 已发布  2 已撤销
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
