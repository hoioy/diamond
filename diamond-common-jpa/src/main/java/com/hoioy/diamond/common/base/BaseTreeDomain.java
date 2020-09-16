package com.hoioy.diamond.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
public class BaseTreeDomain extends BaseDomain {
    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "order_index")
    private Integer orderIndex;
}
