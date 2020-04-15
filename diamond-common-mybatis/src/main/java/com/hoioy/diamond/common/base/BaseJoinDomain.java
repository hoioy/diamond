package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseJoinDomain extends DiamondDomain {

    @TableId(value = "id")
    private String id;
}
