package com.hoioy.jiayin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseTreeDomain;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 消息类型
 * </p>
 *
 * @author 陈哲
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jiayin_zone_code")
@ApiModel(value="ZoneCode对象", description="区划表")
public class ZoneCode extends BaseTreeDomain {

    private static final long serialVersionUID=1L;
    /**
     * 主键
     */
    private String id;
    /**
     * 区划名称
     */
    private String address;
}
