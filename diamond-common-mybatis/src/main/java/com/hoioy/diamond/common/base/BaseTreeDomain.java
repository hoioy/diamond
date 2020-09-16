package com.hoioy.diamond.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseTreeDomain extends BaseDomain {
    private String parentId;
    private Integer orderIndex;
}
