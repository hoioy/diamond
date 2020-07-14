package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinRepository<D extends CommonDomain> extends ICommonRepository<D>, JpaRepository<D, String>, JpaSpecificationExecutor<D> {

}
