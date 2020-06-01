package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ICommonRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface IBaseTreeMapper<D extends BaseTreeDomain> extends BaseMapper<D>, ICommonRepository<D>, IBaseMapper<D> {

}
