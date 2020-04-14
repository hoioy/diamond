package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ITDFRepository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinMapper<T> extends BaseMapper<T>, ITDFRepository {

}
