package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.ITDFRepository;
import com.hoioy.diamond.common.domain.TDFDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinRepository<D extends TDFDomain, ID> extends ITDFRepository<D>, JpaRepository<D, ID>, JpaSpecificationExecutor<D> {

}
