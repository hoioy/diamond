package com.hoioy.diamond.common.base;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinMapper<T extends BaseJoinDomain> extends IBaseCommonRepository<T> {

}
