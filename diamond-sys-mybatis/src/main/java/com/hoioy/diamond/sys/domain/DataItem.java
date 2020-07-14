package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseTreeDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("data_item")
public class DataItem extends BaseTreeDomain {

    private static final long serialVersionUID = 1L;

    private String code;

    private Integer codeIndex;

    private String name;

    private String state;

    private String dataItemTypeId;

}
