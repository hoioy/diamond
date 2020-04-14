package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends IBaseRepository<Menu, String> {
    List<Menu> findByParentId(String parentId);
    List<String> findIdsByMenuUrl(String menuUrl);
}
