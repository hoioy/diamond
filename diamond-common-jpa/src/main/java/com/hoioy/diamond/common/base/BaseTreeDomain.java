package com.hoioy.diamond.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
public class BaseTreeDomain extends BaseDomain {
    @Column(name = "parent_id")
    private String parentId;
}