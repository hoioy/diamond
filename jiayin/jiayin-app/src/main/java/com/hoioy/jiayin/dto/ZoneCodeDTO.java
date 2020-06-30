package com.hoioy.jiayin.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hoioy.diamond.common.base.BaseTreeDomain;
import com.hoioy.diamond.common.dto.BaseTreeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="ZoneCodeDTO", description="区划DTO")
public class ZoneCodeDTO extends BaseTreeDTO {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "区划编码")
    private String id;

    @ApiModelProperty(value = "区划名称")
    private String address;

    @ApiModelProperty(value = "排序")
    private String orderIndex;
}
