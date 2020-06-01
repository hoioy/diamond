package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ICommonRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface IBaseMapper<T extends BaseDomain> extends BaseMapper<T>, ICommonRepository<T> {

}
