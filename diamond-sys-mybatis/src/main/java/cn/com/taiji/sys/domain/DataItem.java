package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("data_item")
public class DataItem extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String code;

    private Integer codeIndex;

    private String name;

    private String state;
}
