package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.IDiamondRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinMapper<T> extends BaseMapper<T>, IDiamondRepository {

}
