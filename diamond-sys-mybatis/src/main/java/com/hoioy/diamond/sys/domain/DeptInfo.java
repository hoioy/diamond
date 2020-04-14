package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@TableName("dept_info")
public class DeptInfo extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String deptDesc;

    private Integer deptIndex;

    private String deptName;

    private String deptState;

    private String deptType;

    private String deptUrl;
}
