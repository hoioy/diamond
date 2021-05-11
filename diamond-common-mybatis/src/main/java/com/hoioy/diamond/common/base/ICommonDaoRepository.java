package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ICommonDaoRepository<D extends CommonDomain> extends ICommonRepository<D>, BaseMapper<D> {

}
