package com.hoioy.diamond.common.base;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IBaseTreeRepository<D extends BaseTreeDomain> extends IBaseRepository<D> {

    /**
     * 根据parentId查询子类
     */
    List<D> findByParentId(String parentId);
}
