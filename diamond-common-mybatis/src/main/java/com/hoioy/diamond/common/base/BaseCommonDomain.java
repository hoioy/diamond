package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class BaseCommonDomain extends CommonDomain {

    @TableId(value = "id")
    private String id;
}
