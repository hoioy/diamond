package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.domain.IDiamondRepository;
import com.hoioy.diamond.common.domain.DiamondDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseJoinRepository<D extends DiamondDomain, ID> extends IDiamondRepository<D>, JpaRepository<D, ID>, JpaSpecificationExecutor<D> {

}
