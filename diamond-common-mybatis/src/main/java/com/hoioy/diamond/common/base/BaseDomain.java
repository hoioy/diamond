package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDomain extends TDFDomain {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableId(value = "parent_id")
    private String parentId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标记
     */
    @TableField(value = "flag", fill = FieldFill.INSERT)
    private Integer flag;

    /**
     * 创建时间
     */
    @TableField(value = "created_date",fill = FieldFill.INSERT)
    private LocalDateTime createdDate;

    /**
     * 修改时间
     */
    @TableField(value = "modified_date",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedDate;

    @TableField(value = "created_by",fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "modified_by",fill = FieldFill.INSERT_UPDATE)
    private String modifiedBy;

    //乐观锁
    @Version
    @TableField("version")
    private Integer version=0;
}
