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
 * 
 * </p>
 *
 * @author 陈哲
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_publish_history")
@ApiModel(value="PublishHistory对象", description="")
public class PublishHistory extends BaseDomain {

    private static final long serialVersionUID=1L;

    /**
     * 发布主键键
     */
    private String publishId;

    /**
     * openid
     */
    private String openid;


    /**
     * 发布类型
     */
    private String publishType;

    /**
     * 发布标题
     */
    private String publishTitle;


}
