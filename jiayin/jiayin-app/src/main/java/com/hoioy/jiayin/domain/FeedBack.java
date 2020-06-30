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
 * 信息类型
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_msg_type")
@ApiModel(value="MsgType对象", description="信息类型")
public class FeedBack extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 图片路径以逗号分割
     */
    private String path;

    /**
     * 联系信息
     */
    private String contactInfo;

    /**
     * 意见问题
     */
    private String content;

}
