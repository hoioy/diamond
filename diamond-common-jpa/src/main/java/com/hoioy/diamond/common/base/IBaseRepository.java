package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ICommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<D extends BaseDomain> extends ICommonRepository<D>, JpaRepository<D, String>, JpaSpecificationExecutor<D> {

}
