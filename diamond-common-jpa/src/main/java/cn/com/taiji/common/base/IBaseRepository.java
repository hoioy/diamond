package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ITDFRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<D extends BaseDomain, ID> extends ITDFRepository<D>, JpaRepository<D, ID>, JpaSpecificationExecutor<D> {

}
