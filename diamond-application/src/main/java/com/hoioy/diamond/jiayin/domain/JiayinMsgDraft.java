package com.hoioy.diamond.jiayin.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 消息草稿
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="JiayinMsgDraft对象", description="消息草稿")
public class JiayinMsgDraft extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息类型
     */
    private Integer msgType;

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
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactPhone;


}
