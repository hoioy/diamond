package com.hoioy.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("advert")
public class Advert extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 广告文字
     */
    private String content;

    /**
     * 广告链接
     */
    private String path;

    /**
     * 广告图片
     */
    private String icon;
}
