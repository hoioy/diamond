package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.TDFDomain;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseJoinDomain extends TDFDomain {

    @TableId(value = "id")
    private String id;
}
