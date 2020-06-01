package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ICommonRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinMapper<T extends BaseJoinDomain> extends BaseMapper<T>, ICommonRepository<T> {

}
